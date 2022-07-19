package com.usercode.util;

import java.util.Scanner;

/**
 * @author sunGuoNan
 * @version 1.0
 * @date 2022/7/18 15:46
 */
public class ScannerUtil {
    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static String input(){
        return SCANNER.next();
    }
}
