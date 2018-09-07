package com.zgc.fds;

import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class apiDoc {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(apiDoc.class);
    private static String path = "/Users/xiaoming/fds/src/main/java/com/zgc/fds/controller/";
    private static String parameterPaths = "/Users/xiaoming/fds/src/main/java/com/zgc/fds/model/httovo/";
    private static String outputPath = "/Users/xiaoming/test/fdsApi.docx";
    private static List<String> pathList = apiDoc.getPathList(path);
    private static List<String> parameterList = apiDoc.getPathList(parameterPaths);

    public static void main(String[] args) throws Exception {
        apiDoc apiDoc = new apiDoc();
        XWPFDocument document = new XWPFDocument();
        int sum = 0;
        for (String l : pathList) {
            try {
                String className = (l.substring(l.indexOf("java/") + 5, l.lastIndexOf("."))).replace("/", ".");
                if (!(className.contains("MqController") || className.contains("TestController"))) {
                    Class<?> cl = Class.forName(className);//类名
                    String content = apiDoc.readToString(l);
                    String baseApi = apiDoc.getBaseApiLocal(content);
                    XWPFParagraph paragraph1 = document.createParagraph();
                    XWPFRun run1 = paragraph1.createRun();
                    run1.setText(l.substring(l.lastIndexOf("/") + 1, l.lastIndexOf(".")) + "的接口");
                    run1.addCarriageReturn();
                    Method[] interfaces = cl.getDeclaredMethods();
                    List<Method> methodList = new ArrayList<>();
                    for (Method m : interfaces) {
                        if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
                            methodList.add(m);
                            sum += 1;
                        }
                    }
                    for (Method a : methodList) {
                        XWPFParagraph paragraph = document.createParagraph();
                        XWPFRun run = paragraph.createRun();
                        FileOutputStream out = new FileOutputStream(new File(outputPath));
                        String localApi = "接口地址:" + baseApi + a.getName() + ".do";
                        String apiMsg = "接口说明:" + apiDoc.getDescription(content, a).trim();
                        String exceptionInfo = "异常类型:";
                        String requestMsg = "请求参数:";
                        Class<?>[] exception = a.getExceptionTypes();
                        if (exception.length > 0) {
                            List<String> exceptionList = new ArrayList<>();
                            for (Class<?> k : exception) {
                                exceptionList.add(k.getName());
                            }
                            for (String e : exceptionList) {
                                exceptionInfo = exceptionInfo + e;
                            }
                        }
                        Class<?> returnType = a.getReturnType();
                        String returnMsg = "返回值类型:" + returnType.getSimpleName();
                        run.setText(localApi);
                        run.addCarriageReturn();
                        run.setText(apiMsg);
                        run.addCarriageReturn();
                        run.setText(returnMsg);
                        run.addCarriageReturn();
                        run.setText(exceptionInfo);
                        run.addCarriageReturn();
                        run.setText(requestMsg);
                        Class<?>[] list = a.getParameterTypes();
                        if (list.length > 0) {
                            XWPFTable table = document.createTable();
                            table.setWidth(1000);
                            XWPFTableRow tableRowOne = table.getRow(0);
                            tableRowOne.getCell(0).setText("参数名");
                            tableRowOne.addNewTableCell().setText("类型");
                            for (int i = 0; i < list.length; i++) {
                                XWPFTableRow tableRowTwo = table.createRow();
                                tableRowTwo.getCell(0).setText(getMethodParameterNamesByAnnotation(a)[i]);
                                tableRowTwo.getCell(1).setText(list[i].getSimpleName());
                            }
                        }
                        for (int i = 0; i < list.length; i++) {
                            if (list[i].getName().contains("com.zgc.fds.model.httovo")) {
                                List<Field> fields = apiDoc.getParameterTypeAndName(list[i]);
                                String parameterPath = "";
                                if (fields.size() > 0) {
                                    for (String h : parameterList) {
                                        if (h.contains(list[i].getSimpleName())) {
                                            parameterPath = h;
                                        }
                                    }
                                    XWPFParagraph paragraph2 = document.createParagraph();
                                    XWPFRun run2 = paragraph2.createRun();
                                    String parMsg = list[i].getSimpleName() + "类型所含字段:";
                                    run2.setText(parMsg);
                                    XWPFTable table1 = document.createTable();
                                    table1.setWidth(1000);
                                    XWPFTableRow tableRowOne = table1.getRow(0);
                                    tableRowOne.getCell(0).setText("参数名");
                                    tableRowOne.addNewTableCell().setText("类型");
                                    tableRowOne.addNewTableCell().setText("说明");
                                    tableRowOne.addNewTableCell().setText("备注");
                                    for (Field f : fields) {
                                        XWPFTableRow tableRowTwo = table1.createRow();
                                        tableRowTwo.getCell(0).setText(f.getName());
                                        tableRowTwo.getCell(1).setText(f.getType().getSimpleName());
                                        if (apiDoc.getParameterIsNotNull(parameterPath, f.getName())) {
                                            tableRowTwo.getCell(2).setText("必填");
                                        } else {
                                            tableRowTwo.getCell(2).setText("选填");
                                        }
                                        tableRowTwo.getCell(3).setText(apiDoc.getParameterDescription(parameterPath, f.getName()));
                                    }
                                }
                            }
                        }
                        document.write(out);
                        logger.info("接口文档一共生成" + sum + "个方法");
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    //读取文件内容为string
    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    //获取接口基本地址
    public String getBaseApiLocal(String content) {
        int index = content.indexOf("@RequestMapping(");
        String localApi;
        for (int i = index + 1; ; i++) {
            if (content.substring(i, i + 1).equals(")")) {
                localApi = "http://ip:port/back/" + content.substring(index + 17, i - 1) + "/";
                break;
            }
        }
        return localApi;
    }

    //获取参数名
    public static String[] getMethodParameterNamesByAnnotation(Method method) {
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] paramNames = discover.getParameterNames(method);//返回的就是方法中的参数名列表了
        return paramNames;
    }

    //获取接口说明
    public String getDescription(String content, Method method) {
        String[] arr = content.split("@Description");
        List<String> list = new ArrayList<>();
        if (arr.length > 1) {
            for (int i = 1; i < arr.length; i++) {
                list.add(arr[i]);
            }
        }
        String str = method.getReturnType().getSimpleName() + " " + method.getName();
        String msg = "";
        for (String entity : list) {
            if (entity.contains(str)) {
                msg = entity.substring(1, entity.indexOf("*"));
            }
        }
        return msg;
    }

    //读取某个路径得到所有文件
    private static List<String> getPathList(String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        List<String> pathList = new ArrayList<>();
        for (File f : tempList) {
            if (f.isDirectory()) {
                pathList.addAll(getPathList(f.getPath()));
            } else {
                pathList.add(f.getPath());
            }
        }
        return pathList;
    }

    //获取某个类的所有参数
    public List<Field> getParameterTypeAndName(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (!(Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers()))) {
                list.add(f);
            }
        }
        return list;
    }

    //获取参数的说明
    public String getParameterDescription(String parameterPath, String ParameterName) {

        String content = readToString(parameterPath);
        String msg = "";
        String[] arr = content.split("@Description");
        List<String> list = new ArrayList<>();
        if (arr.length > 1) {
            for (int i = 1; i < arr.length; i++) {
                list.add(arr[i]);
            }
        }
        for (String entity : list) {
            if (entity.contains(ParameterName) && !entity.contains("get" + ParameterName.substring(0, 1).toUpperCase() + ParameterName.substring(1))) {
                msg = entity.substring(1, entity.indexOf("*"));
                break;
            }
        }
        if (msg == "" && list.size() > 0 && list.get(list.size() - 1).split(ParameterName).length == 6) {
            msg = list.get(list.size() - 1).substring(1, list.get(list.size() - 1).indexOf("*"));
        }
        return msg;
    }

    //获取参数是否必填
    public Boolean getParameterIsNotNull(String parameterPath, String ParameterName) {

        String content = readToString(parameterPath);
        String[] arr = content.split("@Description");
        List<String> list = new ArrayList<>();
        Boolean flag = false;
        if (arr.length > 1) {
            for (int i = 1; i < arr.length; i++) {
                list.add(arr[i]);
            }
        }
        for (String entity : list) {
            if (entity.contains("@NotNull") && entity.contains(ParameterName) && !entity.contains("get" + ParameterName.substring(0, 1).toUpperCase() + ParameterName.substring(1))) {
                flag = true;
                break;
            }
        }
        if (flag == false && list.size() > 0 && list.get(list.size() - 1).split(ParameterName).length == 6 && list.get(list.size() - 1).contains("@NotNull")) {
            flag = true;
        }
        return flag;
    }
}
