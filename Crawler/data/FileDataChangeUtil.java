7
https://raw.githubusercontent.com/zeoio/fabric-toolkit/master/bcp-install-common/src/main/java/com/cgb/bcpinstall/common/util/FileDataChangeUtil.java
/*
 *  Copyright CGB Corp All Rights Reserved.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cgb.bcpinstall.common.util;

import org.apache.commons.io.IOUtils;
import java.io.*;

public class FileDataChangeUtil {
    //从文件中读取文本内容
    public static String getContentFromFile(File file){

        try{
            return getContentFromInputStream(new FileInputStream(file));
        }catch (Exception e){
            e.printStackTrace();
        }
        // return "解析文件失败!!!";
        return "Failed to parse file!!!";
    }

    //从数据流中读取文本内容
    public static String getContentFromInputStream(InputStream inputStream){
        try{
            InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bfreader=new BufferedReader(reader);
            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while((line=bfreader.readLine())!=null) {//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
                stringBuffer.append(line+System.getProperty("line.separator"));
            }
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        // return "解析文件失败!!!";
        return "Failed to parse file!!!";
    }


    //将文件转换成Byte数组
    public static byte[] getBytesFromFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(100000);
            IOUtils.copy(fis, bos);
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getBytesFromInputStream(InputStream inputStream){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(100000);
            IOUtils.copy(inputStream, bos);
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
