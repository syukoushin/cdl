package com.ibm.cdl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created on 2005-8-31
 */
public class StringUtil {


        public static String deleteHtmlFlag(String html)
        {
                //html = html.toLowerCase();
                int startHead = html.indexOf("<head>");
                int endHead = html.indexOf("</head>");
                while (startHead != -1 && startHead < html.length()) {
                        html = html.substring(0, startHead)
                                        + html.substring(endHead+7);
                        startHead = html.indexOf("<head>");
                        endHead = html.indexOf("</head>");
                }
/*
                startHead = html.indexOf("<return");
                if (startHead != -1 && startHead < html.length())
                {
                    endHead = html.indexOf(">",startHead);
                    html = html.substring(0, startHead)+"<return>"
                           + html.substring(endHead+1);
                }
*/
                int startTitle = html.indexOf("<title>");
                int endTitle = html.indexOf("</title>");
                while (startTitle != -1 && startTitle < html.length()) {
                        html = html.substring(0, startTitle)
                                        + html.substring(endTitle+8);
                        startTitle = html.indexOf("<title>");
                        endTitle = html.indexOf("</title>");
                }
                int startStyle = html.indexOf("<style>");
                int endStyle = html.indexOf("</style>");
                while(startStyle != -1 && startStyle < html.length()) {
                        html = html.substring(0, startStyle)
                                        + html.substring(endStyle+8);
                        startStyle = html.indexOf("<style>");
                        endStyle = html.indexOf("</style>");

                }
                html = html.replaceAll("&nbsp;", "");
                html = html.replaceAll("&", "&amp;");
                html = html.replaceAll("cdata","CDATA");
                return html;
        }

        public String deleteHtmlTag(String html) {
                int count = html.length();
                html = html.toLowerCase();
                int startTitle = html.indexOf("<title>");
                int endTitle = html.indexOf("</title>");
                if (startTitle != -1 && startTitle < html.length()) {
                        html = html.substring(0, startTitle)
                                        + html.substring(startTitle, endTitle + 1)
                                        + html.substring(endTitle);

                }
                int startStyle = html.indexOf("<style>");
                int endStyle = html.indexOf("</style>");
                if (startStyle != -1 && startStyle < html.length()) {
                        html = html.substring(0, startStyle)
                                        + html.substring(startStyle, endStyle + 1)
                                        + html.substring(endStyle);

                }

                while (true) {

                        int first = html.indexOf("<");
                        if (first != -1 && first < html.length()) {
                                int last = html.substring(first).indexOf(">");
                                if (last == -1)
                                        break;

                                html = html.substring(0, first)
                                                + html.substring(first + last + 1);
                                html = html.trim();
                        } else {
                                break;
                        }

                }
                html = html.replaceAll("&nbsp;", "");
                html = html.replaceAll("&", "&amp;");
                html = html.replaceAll("<", "&lt;");
                html = html.replaceAll(">", "&gt;");
                html = html.replaceAll("“", "&quot;");
                html = html.replaceAll("”", "&quot;");
                html = html.replaceAll("'", "&apos");
                html = html.replaceAll("！", "!");
                html = html.replaceAll("％", " ");
                html = html.replaceAll("＊", " ");
                html = html.replaceAll("（", "(");
                html = html.replaceAll("）", ")");
                html = html.replaceAll("＄", " ");
                html = html.replaceAll("￥", " ");
                html = html.replaceAll("\n", " ");

                return html;
        }

        public String deleteHtmlTagTemp(String html) {

          int count = html.length();

          while (true) {
            int first = html.indexOf("<");

            if (first != -1 && first < html.length()) {
              int last = html.substring(first).indexOf(">");
              if (last == -1) {
                break;
              }
              html = html.substring(0, first) + html.substring(first + last + 1);
            }
            else {
              break;
            }

          }
          return html;
        }

        public String dealHtmlContent(String html) {
          int count = html.length();
          while (true) {
            int first = html.indexOf("<del");
            if (first != -1 && first < html.length()) {
              int last = html.substring(first).indexOf("/del>");
              if (last == -1) {
                break;
              }
              html = html.substring(0, first) + html.substring(first + last + 5);
            }
            else {
              break;
            }
          }
          html = dealHtmlTag(html,"");
          html = deleteHtmlTagTemp(html);
          html = removeBlankLine(html);
          return html;
        }

        public String dealHtmlTag(String html,String tag) {
          int count = html.length();
          while (true) {
            int first = html.indexOf("<"+tag);
            if (first != -1 && first < html.length()) {
              int last = html.substring(first).indexOf("/"+tag+">");
              if (last == -1) {
                break;
              }
              html = html.substring(0, first) + html.substring(first + last + tag.length()+2);
            }
            else {
              break;
            }
          }
          return html;
        }

        public String deleteHtmlTag1(String html) {

                html = html.replaceAll("&amp;lt;", "&lt;");
                html = html.replaceAll("&amp;gt;", "&gt;");
                html = html.replaceAll("'", "");
                html = html.replaceAll("<可以在这里输入其他出席人>", "");
                return html;
        }

        public String deleteHtmlTag2(String html) {
                int count = html.length();
                int startTitle = html.indexOf("<title>");
                int endTitle = html.indexOf("</title>");
                if (startTitle != -1 && startTitle < html.length()) {
                        html = html.substring(0, startTitle)
                                        + html.substring(startTitle, endTitle + 1)
                                        + html.substring(endTitle);

                }
                int startStyle = html.indexOf("<style>");
                int endStyle = html.indexOf("</style>");
                if (startStyle != -1 && startStyle < html.length()) {
                        html = html.substring(0, startStyle)
                                        + html.substring(startStyle, endStyle + 1)
                                        + html.substring(endStyle);

                }

                while (true) {

                        int first = html.indexOf("<");
                        if (first != -1 && first < html.length()) {
                                int last = html.substring(first).indexOf(">");
                                if (last == -1)
                                        break;

                                html = html.substring(0, first)
                                                + html.substring(first + last + 1);
                                html = html.trim();
                        } else {
                                break;
                        }

                }
                html = html.replaceAll("&nbsp;", "");
                html = html.replaceAll("&", "&amp;");
                html = html.replaceAll("<", "&lt;");
                html = html.replaceAll(">", "&gt;");
                html = html.replaceAll("“", "&quot;");
                html = html.replaceAll("”", "&quot;");
                html = html.replaceAll("'", "&apos");
                html = html.replaceAll("！", "!");
                html = html.replaceAll("％", "%");
                html = html.replaceAll("＊", "%");
                html = html.replaceAll("（", "(");
                html = html.replaceAll("）", ")");
                html = html.replaceAll("＄", "$");
                html = html.replaceAll("￥", "$");
                html = html.replaceAll("\r", "");
                return html;
        }

        public ArrayList gerArry(String arg) {
                ArrayList list=new ArrayList();
                if (arg != null) {
                        StringTokenizer st = new StringTokenizer(arg, ";, ");
                        while (st.hasMoreTokens()) {
                                String ar = st.nextToken();
                                list.add(ar);
                                //System.out.println("----获得邮件地址--" + ar);

                        }
                        return list;
                } else
                        return list;
        }

        // public static String CheckStr(String str)
        // {
        // str = str.replaceAll("<", " ");
        // str = str.replaceAll(">", " ");
        // str = str.replaceAll("'", "''");
        // str = str.replaceAll("&", " ");
        // str = str.replaceAll(" ", " ");
        // str = str.replaceAll("\n", "<br/>");
        // str = str.replaceAll("\r\n", "<br/>");
        // str = str.trim();
        // return str;
        // }

        public static boolean checkIfWindowsOS() {
                Properties p = System.getProperties();
                String osname = (String) p.get("os.name");
                if (osname.indexOf("Windows") != -1) {
                        return true;
                } else
                        return false;
        }

        public String removeBlankLine(String html)
        {
          int count = html.length();
          int first,next;
          first = html.indexOf('\n');
          while(first != -1 && first < count)
          {
              next = html.indexOf('\n', first + 1);
              if (next != -1 && next < count) {
                boolean flag = true;
                for(int i=first+1;i<next;i++){
                  char c = html.charAt(i);
                  if(c!=' ' && c!='　' && c!='\r'){
                    flag= false;
                    break;
                  }
                }
                if(flag){//remove
                  html = html.substring(0, first+1) + html.substring(next + 1);
                  count = html.length();
                }else{
                  first = next;
                }
              }else{
                break;
              }
          }
          return html;
        }

        public static String convertExpToString(Exception e)
        {
          String result = "";
          try
          {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            pw.close();
            sw.flush();
            result = sw.toString();
            sw.close();
          } catch (Exception ee) {
            ee.printStackTrace();
          }

          return result;
        }
        
        public static void main(String[] args) {
                StringUtil s = new StringUtil();
        }
}
