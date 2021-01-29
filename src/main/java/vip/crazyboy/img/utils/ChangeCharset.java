package vip.crazyboy.img.utils;

import java.io.UnsupportedEncodingException;

/**
 * 转换字符串的编码
 */
public class ChangeCharset {
    /** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
    public static final String US_ASCII = "US-ASCII";

    /** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /** 8 位 UCS 转换格式 */
    public static final String UTF_8 = "UTF-8";

    /** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
    public static final String UTF_16BE = "UTF-16BE";

    /** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
    public static final String UTF_16LE = "UTF-16LE";

    /** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
    public static final String UTF_16 = "UTF-16";

    /** 中文超大字符集 */
    public static final String GBK = "GBK";

    /**
     * 将字符编码转换成US-ASCII码
     */
    public String toASCII(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, US_ASCII);
    }
    /**
     * 将字符编码转换成ISO-8859-1码
     */
    public String toISO_8859_1(String str) throws UnsupportedEncodingException{
        return this.changeCharset(str, ISO_8859_1);
    }
    /**
     * 将字符编码转换成UTF-8码
     */
    public String toUTF_8(String str) throws UnsupportedEncodingException{
        return this.changeCharset(str, UTF_8);
    }
    /**
     * 将字符编码转换成UTF-16BE码
     */
    public String toUTF_16BE(String str) throws UnsupportedEncodingException{
        return this.changeCharset(str, UTF_16BE);
    }
    /**
     * 将字符编码转换成UTF-16LE码
     */
    public String toUTF_16LE(String str) throws UnsupportedEncodingException{
        return this.changeCharset(str, UTF_16LE);
    }
    /**
     * 将字符编码转换成UTF-16码
     */
    public String toUTF_16(String str) throws UnsupportedEncodingException{
        return this.changeCharset(str, UTF_16);
    }
    /**
     * 将字符编码转换成GBK码
     */
    public String toGBK(String str) throws UnsupportedEncodingException{
        return this.changeCharset(str, GBK);
    }

    /**
     * 字符串编码转换的实现方法
     * @param str 待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }
    /**
     * 字符串编码转换的实现方法
     * @param str 待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String oldCharset, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        ChangeCharset test = new ChangeCharset();
//        String str = "This is a 中文的 String!";
        String str = "{\"code\":0,\"data\":\"%5B%7B%22wxid%22%3A%2211540105536%40chatroom%22%2C%22nickname%22%3A%22%E5%AE%B6%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2217221576225%40chatroom%22%2C%22nickname%22%3A%22%E5%A5%87%E5%AD%90%E5%A4%A7%E4%BD%AC%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2213407942954%40chatroom%22%2C%22nickname%22%3A%22%E7%9A%87%E4%BA%B2%E5%9B%BD%E6%88%9A%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%222221218143%40chatroom%22%2C%22nickname%22%3A%22%E9%AB%98%E4%B8%80%E4%B8%89%E7%8F%AD%EF%BC%8C%EF%BC%88%E5%90%8C%E5%AD%A6%EF%BC%89%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%224573631556%40chatroom%22%2C%22nickname%22%3A%22coding%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%227436885716%40chatroom%22%2C%22nickname%22%3A%22808%E8%81%8A%E5%A4%A9%E5%AE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%229097050026%40chatroom%22%2C%22nickname%22%3A%22%E5%90%83%E9%B8%A1%E5%B0%8F%E5%88%86%E9%98%9F%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%227327862488%40chatroom%22%2C%22nickname%22%3A%22%E7%9B%B8%E4%BA%B2%E7%9B%B8%E7%88%B1%E4%B8%80%E5%AE%B6%E4%BA%BA%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2212943920107%40chatroom%22%2C%22nickname%22%3A%22%E5%AE%B6%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%226435236818%40chatroom%22%2C%22nickname%22%3A%22%E4%B8%80%E5%AE%B6%E4%BA%BA%E5%88%98%E5%BA%84%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%226836782793%40chatroom%22%2C%22nickname%22%3A%22%E5%AE%B6%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%222750449706%40chatroom%22%2C%22nickname%22%3A%22%E6%95%AC%E5%90%9B%E7%A5%9E%E9%80%9A%2C%20%E4%BC%8F%E5%9C%B0%E5%91%BC%E5%85%84%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2218707322141%40chatroom%22%2C%22nickname%22%3A%22%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%225543335365%40chatroom%22%2C%22nickname%22%3A%22%E6%8A%80%E6%9C%AF%E7%B2%BE%E8%8B%B1%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%225802416223%40chatroom%22%2C%22nickname%22%3A%22%E5%AD%A9%E5%AD%90%E7%8E%8B%E5%92%8C%E5%AD%A9%E5%AD%90%E4%BB%AC%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%228277674343%40chatroom%22%2C%22nickname%22%3A%22%E6%81%92%E5%A4%A7%E9%83%BD%E5%B8%82%E5%B9%BF%E5%9C%BA%E4%B8%9A%E4%B8%BB%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2219649587250%40chatroom%22%2C%22nickname%22%3A%22%E5%B9%BF%E5%9C%BA%E8%AF%89%E8%AE%BC%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%222195319315%40chatroom%22%2C%22nickname%22%3A%22%E5%88%98%E5%BA%84%E8%A5%BF%E9%98%9F%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2217753272239%40chatroom%22%2C%22nickname%22%3A%22%E8%A5%BF%E5%AE%89%E6%81%92%E5%A4%A7%E9%83%BD%E5%B8%82%E5%B9%BF%E5%9C%BA%E5%87%BA%E7%A7%9F%E6%88%BF%E6%BA%90%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2218394414377%40chatroom%22%2C%22nickname%22%3A%22%E5%B9%BF%E5%9C%BA%E8%AF%89%E8%AE%BC%E7%BE%A4%EF%BC%88%E6%B2%A1%E5%BE%8B%E5%B8%88%EF%BC%89%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2223975756572%40chatroom%22%2C%22nickname%22%3A%22%E4%B8%BA%E6%A2%A6%E6%83%B3%E8%80%8C%E6%88%98%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%225330375200%40chatroom%22%2C%22nickname%22%3A%22%E6%88%BF%E5%93%A5%26%E6%88%BF%E5%8F%94%E4%BA%A4%E6%B5%817%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%226987658877%40chatroom%22%2C%22nickname%22%3A%22%E5%8F%B8%E6%9C%BA%E7%AB%AF4.0%E8%B7%AF%E6%B5%8B%E5%8F%8D%E9%A6%88%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2222880106254%40chatroom%22%2C%22nickname%22%3A%22%3F%3F1-%E5%A4%9A%E7%BA%BF%E7%A8%8B%E9%AB%98%E5%B9%B6%E5%8F%91%E7%8F%AD%E7%BA%A7%E7%BE%A4-1%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2221599164216%40chatroom%22%2C%22nickname%22%3A%22%E8%B5%84%E6%BA%90%E5%88%86%E4%BA%AB%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%225082200292%40chatroom%22%2C%22nickname%22%3A%22%E4%B8%83%E4%BA%BA%E8%A1%8C%E5%95%8A%E8%A1%8C%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%227144902591%40chatroom%22%2C%22nickname%22%3A%22%E6%B4%BE%E5%8D%95%E6%8A%80%E6%9C%AF%E8%AE%A8%E8%AE%BA%E7%BB%84%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2224844367364%40chatroom%22%2C%22nickname%22%3A%22%E8%81%94%E8%81%94%E5%8C%97%E4%BA%AC%E8%BE%BE%E4%BA%BA%E7%BE%A46%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2217998797435%40chatroom%22%2C%22nickname%22%3A%22%E6%89%8B%E6%9C%BA%E7%8E%B0%E8%B4%A7%E6%B7%B1%E5%9C%B3%E6%94%B6%E8%B4%A7%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2218174983296%40chatroom%22%2C%22nickname%22%3A%22%E6%81%92%E5%A4%A7%E9%83%BD%E5%B8%82%E5%B9%BF%E5%9C%BA9%E5%8F%B7%E6%A5%BC%EF%BC%88%E4%B8%93%E5%B1%9E%EF%BC%89%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2222943149827%40chatroom%22%2C%22nickname%22%3A%22%E5%88%98%E5%BA%84%E6%9D%91%E7%BE%A4%E4%BC%97%E5%B7%A5%E4%BD%9C%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%224714127205%40chatroom%22%2C%22nickname%22%3A%22%E6%81%92%E5%A4%A7%E9%83%BD%E5%B8%82%E5%B9%BF%E5%9C%BA%E4%B8%9A%E4%B8%BB%E6%80%BB%E7%BE%A42%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2222604144928%40chatroom%22%2C%22nickname%22%3A%22%E5%BE%AE%E4%BF%A1%E6%AF%8F%E6%97%A5%E8%AF%B4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2223410889955%40chatroom%22%2C%22nickname%22%3A%22Lighthouse%E5%85%AC%E6%B5%8B%E5%8F%8D%E9%A6%886%E7%BE%A4%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2224095470540%40chatroom%22%2C%22nickname%22%3A%22%E9%83%BD%E5%B8%829-1%EF%BD%9E%E6%AC%A2%E5%A4%A9%E5%96%9C%E5%9C%B0%E4%B8%80%E5%AE%B6%E4%BA%BA%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%2C%7B%22wxid%22%3A%2224106256445%40chatroom%22%2C%22nickname%22%3A%22%E8%BE%BE%E4%BA%BA033%E7%BE%A4%20%7C%20%E8%81%94%E8%81%94%E5%8C%97%E4%BA%AC%E7%AB%99%22%2C%22robot_wxid%22%3A%22A903866501%22%7D%5D\"}";
        System.out.println("str: " + str);
        String gbk = test.toGBK(str);
        System.out.println("转换成GBK码: " + gbk);
        System.out.println();
        String ascii = test.toASCII(str);
        System.out.println("转换成US-ASCII码: " + ascii);
        gbk = test.changeCharset(ascii,ChangeCharset.US_ASCII, ChangeCharset.GBK);
        System.out.println("再把ASCII码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String iso88591 = test.toISO_8859_1(str);
        System.out.println("转换成ISO-8859-1码: " + iso88591);
        gbk = test.changeCharset(iso88591,ChangeCharset.ISO_8859_1, ChangeCharset.GBK);
        System.out.println("再把ISO-8859-1码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf8 = test.toUTF_8(str);
        System.out.println("转换成UTF-8码: " + utf8);
        gbk = test.changeCharset(utf8,ChangeCharset.UTF_8, ChangeCharset.GBK);
        System.out.println("再把UTF-8码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf16be = test.toUTF_16BE(str);
        System.out.println("转换成UTF-16BE码:" + utf16be);
        gbk = test.changeCharset(utf16be,ChangeCharset.UTF_16BE, ChangeCharset.GBK);
        System.out.println("再把UTF-16BE码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf16le = test.toUTF_16LE(str);
        System.out.println("转换成UTF-16LE码:" + utf16le);
        gbk = test.changeCharset(utf16le,ChangeCharset.UTF_16LE, ChangeCharset.GBK);
        System.out.println("再把UTF-16LE码的字符串转换成GBK码: " + gbk);
        System.out.println();
        String utf16 = test.toUTF_16(str);
        System.out.println("转换成UTF-16码:" + utf16);
        gbk = test.changeCharset(utf16,ChangeCharset.UTF_16LE, ChangeCharset.GBK);
        System.out.println("再把UTF-16码的字符串转换成GBK码: " + gbk);
        String s = new String("中文".getBytes("UTF-8"),"UTF-8");
        System.out.println(s);
    }

    /**
     * UCS2解码
     *
     * @param src
     *            UCS2 源串
     * @return 解码后的UTF-16BE字符串
     */
    public   static  String DecodeUCS2(String src) {
        byte [] bytes =  new   byte [src.length() /  2 ];

        for  ( int  i =  0 ; i < src.length(); i +=  2 ) {
            bytes[i / 2 ] = ( byte ) (Integer
                    .parseInt(src.substring(i, i + 2 ),  16 ));
        }
        String reValue = null;
        try  {
            reValue = new  String(bytes,  "UTF-16BE" );
        } catch  (UnsupportedEncodingException e) {

        }
        return  reValue;

    }

    /**
     * UCS2编码
     *
     * @param src
     *            UTF-16BE编码的源串
     * @return 编码后的UCS2串
     */
    public   static  String EncodeUCS2(String src) {

        byte [] bytes = null;
        try  {
            bytes = src.getBytes("UTF-16BE" );
        } catch  (UnsupportedEncodingException e) {
        }

        StringBuffer reValue = new  StringBuffer();
        StringBuffer tem = new  StringBuffer();
        for  ( int  i =  0 ; i < bytes.length; i++) {
            tem.delete(0 , tem.length());
            tem.append(Integer.toHexString(bytes[i] & 0xFF ));
            if (tem.length()== 1 ){
                tem.insert(0 , '0');
            }
            reValue.append(tem);
        }
        return  reValue.toString().toUpperCase();
    }

}