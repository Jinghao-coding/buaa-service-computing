import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
    /**
     * jvm 参数形式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
        customLogic(inst);
    }

    /**
     * 动态 attach 方式启动，运行此方法
     *
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain");
        customLogic(inst);
    }
    /**
     * 统计方法耗时
     *
     * @param inst
     */
    private static void customLogic(Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
                                @Override
                                public byte[] transform(ClassLoader loader, String className,
                                                        Class<?> classBeingRedefined,
                                                        ProtectionDomain protectionDomain,
                                                        byte[] classfileBuffer) throws IllegalClassFormatException {
                                    // 只针对目标包下进行耗时统计
                                    if (!className.startsWith("org/apache/hadoop")) {
                                        return classfileBuffer;
                                    }

                                    CtClass cl = null;
                                    try {
                                        ClassPool classPool = ClassPool.getDefault();
                                        cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                                        for (CtMethod method : cl.getDeclaredMethods()) {
                                            // 所有方法，统计耗时；请注意，需要通过`addLocalVariable`来声明局部变量
                                            method.addLocalVariable("start", CtClass.longType);
                                            method.addLocalVariable("pid", CtClass.longType);
                                            method.insertBefore("start = System.currentTimeMillis(); " +
                                                    "pid = Thread.currentThread().getId();");
                                            String methodName = method.getLongName();
                                            method.insertAfter("System.err.println(\"" + methodName + " , \" + (System" +
                                                    ".currentTimeMillis() - start) + \" , \" + start + \" ,  \" + pid);");
                                        }

                                        byte[] transformed = cl.toBytecode();
                                        return transformed;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return classfileBuffer;
                                }
                            },
                true);
    }

}
