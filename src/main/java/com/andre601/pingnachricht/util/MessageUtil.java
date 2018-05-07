package com.andre601.pingnachricht.util;

public class MessageUtil {

    /*
    * System to convert &[code] into the corresponding color (§[code])
    * This was used from LuckPerms (https://github.com/lucko/LuckPerms)
     */
    public static String color(String s){
        char[] b = s.toCharArray();

        for(int i = 0; i < b.length - 1; ++i){
            if(b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1){
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}
