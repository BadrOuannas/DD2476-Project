5
https://raw.githubusercontent.com/sstewartgallus/jsystemf/master/src/com/sstewartgallus/runtime/LdcStub.java
package com.sstewartgallus.runtime;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;

import java.lang.constant.DirectMethodHandleDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.lookup;
import static java.lang.invoke.MethodType.methodType;
import static org.objectweb.asm.Opcodes.*;

public abstract class LdcStub<T> {
    public static MethodHandle spin(MethodHandles.Lookup lookup, Class<?> kType, DynamicConstantDesc<?> constantDesc) {
        return spin(lookup, kType, toObjectWeb(constantDesc));
    }

    private static ConstantDynamic toObjectWeb(DynamicConstantDesc<?> k) {
        return new ConstantDynamic(k.constantName(), k.constantType().descriptorString(), toObjectWeb(k.bootstrapMethod()), k.bootstrapArgs());
    }

    private static Handle toObjectWeb(DirectMethodHandleDesc k) {
        return new Handle(k.refKind(), Type.getType(k.owner().descriptorString()).getInternalName(), k.methodName(), k.lookupDescriptor(), k.isOwnerInterface());
    }

    private static MethodHandle spin(MethodHandles.Lookup lookup, Class<?> kType, ConstantDynamic constantDesc) {
        var myname = Type.getInternalName(LdcStub.class);
        var newclassname = myname + "Impl";

        // fixme... privatise as much as possible...
        var cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V14, ACC_FINAL | ACC_PUBLIC, newclassname, null, null, null);

        {
            var mw = cw.visitMethod(ACC_PUBLIC, "resolve", methodType(kType).descriptorString(), null, null);
            mw.visitCode();
            mw.visitLdcInsn(constantDesc);
            mw.visitInsn(ARETURN);
            mw.visitMaxs(0, 0);
            mw.visitEnd();
        }

        cw.visitEnd();

        var bytes = cw.toByteArray();

        var klass = AnonClassLoader.defineClass(lookup.lookupClass().getClassLoader(), bytes);

        MethodHandle resolve;
        try {
            resolve = lookup().findStatic(klass, "resolve", methodType(kType));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return resolve;
    }
}

