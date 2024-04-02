package com.nndmove.app.ultils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaParserUtils {

    public static Map<String, FieldDeclaration> getFieldNameMap(File file) {
        try {
            CompilationUnit cu = new JavaParser().parse(file).getResult().orElseThrow();
            return cu
                .findAll(FieldDeclaration.class)
                .stream()
                .collect(Collectors.toMap(o -> o.getVariable(0).getNameAsString(), o -> o, (o1, o2) -> o1));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getTypeAsString(Type type) {
        if (type instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType) type;
            StringBuilder sb = new StringBuilder();
            sb.append(classOrInterfaceType.getNameAsString());
            if (classOrInterfaceType.getTypeArguments().isPresent()) {
                sb.append("<");
                List<Type> typeArgs = classOrInterfaceType.getTypeArguments().get();
                for (int i = 0; i < typeArgs.size(); i++) {
                    sb.append(getTypeAsString(typeArgs.get(i)));
                    if (i < typeArgs.size() - 1) {
                        sb.append(",");
                    }
                }
                sb.append(">");
            }
            return sb.toString();
        } else {
            return type.toString();
        }
    }
}
