package com.nndmove.app.ultils;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.nndmove.app.domain.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class DTOGenerator {

    private static final Template dtoTemplate = Velocity.getTemplate("src/main/resources/config/velocity/EntityDTO.vm");
    //    private static final Template responseTemplate = Velocity.getTemplate(
    //        "src/main/resources/config/velocity/EntityRS.vm");
    private static final Template mapperTemplate = Velocity.getTemplate("src/main/resources/config/velocity/EntityMapperDTO.vm");

    //    private static final String rqPackage = "io.opt_team.tms.service.request";

    private static final String dtoPackage = "com.nndmove.app.service.dto";
    private static final String mapperPackage = "com.nndmove.app.service.mapper";

    //    private VelocityContext requestContext;
    private VelocityContext dtoContext;
    private VelocityContext mapperContext;

    @Accessors(chain = true)
    public static class NestedField {

        private String fieldName;
        private String fieldNameGetter;
        private String entityName;
        private String className;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldNameGetter() {
            return fieldNameGetter;
        }

        public void setFieldNameGetter(String fieldNameGetter) {
            this.fieldNameGetter = fieldNameGetter;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    private void setContext(Class<?> clazz) {
        //        requestContext = new VelocityContext();
        dtoContext = new VelocityContext();
        mapperContext = new VelocityContext();
        // Danh sách field của class EntityRQ
        //        List<String> requestFields = new ArrayList<>();

        // Danh sách field của class EntityRS
        List<String> dtoFields = new ArrayList<>();

        Set<NestedField> nestFieldNameIds = new TreeSet<>(Comparator.comparing(NestedField::getFieldName));
        Set<NestedField> nestFieldNameJoinManyIds = new TreeSet<>(Comparator.comparing(NestedField::getFieldName));
        Set<String> dtoAdditionImports = new HashSet<>();
        Set<String> mapperAdditionImports = new HashSet<>();

        //        Set<String> toSetIdsConverterEntities = new HashSet<>();

        Map<String, FieldDeclaration> fieldNameMap = JavaParserUtils.getFieldNameMap(
            new File("src/main/java/com/nndmove/app/domain/" + clazz.getSimpleName() + ".java")
        );

        // Duyệt qua từng field của class Entity và các class con bên trong nó
        Field[] declaredFields = clazz.getDeclaredFields();
        //        Arrays.sort(declaredFields, Comparator.comparing(Field::getName));
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            // Tên field
            String fieldName;
            String fieldType;

            if (field.getType().isPrimitive() || field.getType().getCanonicalName().startsWith("java.lang")) {
                fieldName = field.getName();
                fieldType = field.getType().getSimpleName();
            } else if (field.getType().isEnum()) {
                fieldName = field.getName();
                fieldType = field.getType().getSimpleName();
                dtoAdditionImports.add(field.getType().getCanonicalName());
            } else if (field.getType().equals(ZonedDateTime.class)) {
                fieldName = field.getName();
                fieldType = field.getType().getSimpleName();
                dtoAdditionImports.add("java.time.ZonedDateTime");
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                fieldName = field.getName() + "Ids";
                fieldType = "Set<Long>";

                dtoAdditionImports.add("java.util.Set");
                dtoAdditionImports.add("java.util.stream.Collectors");

                List<Node> typeChildNodes = fieldNameMap.get(field.getName()).getElementType().getChildNodes();
                Node genericTypeNode = typeChildNodes.get(typeChildNodes.size() - 1);
                NestedField nestedField = new NestedField();
                nestedField.setFieldName(fieldName);
                nestedField.setFieldNameGetter(fieldToGetter(fieldName));
                nestedField.setEntityName(genericTypeNode.toString());
                nestFieldNameJoinManyIds.add(nestedField);
            } else {
                fieldName = field.getName();
                NestedField nestedField = new NestedField();
                nestedField.setFieldName(fieldName);
                nestedField.setFieldNameGetter(fieldToGetter(field.getName()));
                nestedField.setClassName(field.getType().getSimpleName());
                nestFieldNameIds.add(nestedField);

                fieldName = fieldName + "Id";
                fieldType = "Long";
            }

            //            requestFields.add("private " + fieldType + " " + fieldName + ";");
            dtoFields.add("private " + fieldType + " " + fieldName + ";");
        }
        mapperAdditionImports.add("java.util.Set");
        mapperAdditionImports.add("java.util.stream.Collectors");
        //        requestContext.put("requestFields", requestFields);
        dtoContext.put("dtoFields", dtoFields);
        //        requestContext.put("additionImports", additionImports);
        dtoContext.put("additionImports", dtoAdditionImports);

        mapperContext.put("nestFieldNameIds", nestFieldNameIds);
        mapperContext.put("nestFieldNameJoinManyIds", nestFieldNameJoinManyIds);
        //        mapperContext.put("toSetIdsConverterEntities", toSetIdsConverterEntities);

        //        requestContext.put("entityName", clazz.getSimpleName());
        dtoContext.put("entityName", clazz.getSimpleName());
        mapperContext.put("entityName", clazz.getSimpleName());
        mapperContext.put("additionImports", mapperAdditionImports);
    }

    private static String fieldToGetter(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    private void writeToFile() throws IOException {
        // Tạo writer để ghi file
        //        StringWriter requestWriter = new StringWriter();
        StringWriter dtoWriter = new StringWriter();
        StringWriter mapperWriter = new StringWriter();

        // Merge context với template
        //        requestTemplate.merge(requestContext, requestWriter);
        dtoTemplate.merge(dtoContext, dtoWriter);
        mapperTemplate.merge(mapperContext, mapperWriter);

        // Ghi file
        String dtoFileName = "src/main/java/" + dtoPackage.replace(".", "/") + "/" + dtoContext.get("entityName") + "DTO.java";
        //        String responseFileName = "src/main/java/" + rsPackage.replace(".", "/") +
        //            "/" + responseContext.get("entityName") + "RS.java";
        String mapperFileName = "src/main/java/" + mapperPackage.replace(".", "/") + "/" + mapperContext.get("entityName") + "Mapper.java";

        FileUtils.writeStringToFile(new File(dtoFileName), dtoWriter.toString(), StandardCharsets.UTF_8);
        //        FileUtils.writeStringToFile(new File(responseFileName), responseWriter.toString(), StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(mapperFileName), mapperWriter.toString(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        List<Class<? extends Serializable>> entityClasses = List.of(
            User.class,
            Authority.class,
            Genres.class,
            History.class,
            Movie.class,
            MovieGenres.class,
            MovieResource.class,
            Payment.class,
            Playlist.class,
            Premium.class
        );
        DTOGenerator DTOGenerator = new DTOGenerator();
        for (Class<? extends Serializable> entityClass : entityClasses) {
            DTOGenerator.setContext(entityClass);
            DTOGenerator.writeToFile();
        }
        // log if success
        System.out.println("Generate DTO and Mapper successfully");
    }
}
