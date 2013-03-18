package com.doudoumobile.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Cn2Char {
	
	/**  
     * 汉字转换位汉语拼音首字母，英文字符不变  
     * @param chines 汉字  
     * @return 拼音  
     */     
    public static String getFirstCharacter(String cnString){             
    	
    	char pinyinName = 0;      
        char[] nameChar = cnString.toCharArray();      
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();      
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);      
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);      
        
        if (nameChar[0] > 128) {      
            try {      
                 pinyinName = PinyinHelper.toHanyuPinyinStringArray(nameChar[0], defaultFormat)[0].charAt(0);      
             } catch (BadHanyuPinyinOutputFormatCombination e) {      
                 e.printStackTrace();      
             }      
         }else{      
             pinyinName = Character.toUpperCase(nameChar[0]);      
         }      
        
        return String.valueOf(pinyinName);      
     }     

}
