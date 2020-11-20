package org.mine.aplt.codeGenerator;

import org.mine.aplt.util.CommonUtils;

import java.io.File;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: CodeDelete
 * @date 2020/9/1419:17
 */
public class CodeDelete {
    private static final String PACKAGE_BASE = "E:\\IdeaWorkSpace\\spring-web\\newspro\\src\\main\\";
    private static final String PACKAGE_MODEL = PACKAGE_BASE + "java\\org\\mine\\model\\";
    private static final String PACKAGE_DAO = PACKAGE_BASE + "java\\org\\mine\\dao\\";
    private static final String PACKAGE_DAO_IMPL = PACKAGE_DAO + "impl\\";
    private static final String PACKAGE_MAPPER = PACKAGE_BASE + "resources\\config\\mybatis\\mapper\\";
    private static final String SUFFIX_MODEL = ".java";
    private static final String SUFFIX_DAO = "Dao.java";
    private static final String SUFFIX_DAO_IMPL = "DaoImpl.java";
    private static final String SUFFIX_MAPPER = "Mapper.xml";

    public static void main(String[] args) {
        String[] ds = new String[]{"batch_db_lock"};
        String modelName = "", daoName = "", daoImplName = "", mapperName = "";
        for (String s : ds) {
            File modelFile = new File(modelName = PACKAGE_MODEL + CommonUtils.underLineToHump(s, false) + SUFFIX_MODEL);
            if (modelFile.exists() && modelFile.isFile()) {
                modelFile.delete();
                System.out.println(modelName + " is deleted.");
            }
            File daoFile = new File(daoName = PACKAGE_DAO + CommonUtils.underLineToHump(s, false) + SUFFIX_DAO);
            if (daoFile.exists() && daoFile.isFile()) {
                daoFile.delete();
                System.out.println(daoName + " is deleted.");
            }
            File daoImplFile = new File(daoImplName = PACKAGE_DAO_IMPL + CommonUtils.underLineToHump(s, false) + SUFFIX_DAO_IMPL);
            if (daoImplFile.exists() && daoImplFile.isFile()) {
                daoImplFile.delete();
                System.out.println(daoImplName + " is deleted.");
            }
            File mapperFile = new File(mapperName = PACKAGE_MAPPER + CommonUtils.underLineToHump(s, false) + SUFFIX_MAPPER);
            if (mapperFile.exists() && mapperFile.isFile()) {
                mapperFile.delete();
                System.out.println(mapperName + " is deleted.");
            }
        }
    }
}
