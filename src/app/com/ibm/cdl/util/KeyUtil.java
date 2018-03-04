package com.ibm.cdl.util;


public class KeyUtil {
	
	
	private static String[] key_1=new String[]{"A","B","C"};
	private static String[] key_2=new String[]{"=","-","%"};

	
	public static void main(String[] args) throws Exception{
		String zifchuan="中国移动";
		String z1=encryptHex(zifchuan);
		String z2=decryptHex(z1);
		System.out.println("加密前:"+zifchuan+"\t"+"长度:"+zifchuan.length());
		System.out.println("加密后:"+z1+"\t"+"长度:"+z1.length());
		System.out.println("解密后:"+z2+"\t"+"长度:"+z2.length());
	}

  	/****************************************************************
  	 *   十六进制 加解密
  	 *****************************************************************/
    
	/**
	 * 十六进制加密
	 * @param sourceStr 源字符串
	 * @return
	 */
	public static String encryptHex(String sourceStr){
		String encryptStr=sourceStr;
		if(!sourceStr.equals("")){
			try{
				String tempStr=parseByte2HexStr(sourceStr);
				char[] tempChars=tempStr.toCharArray();
				int clen=tempChars.length;
				String[] tempS=null;
				if(clen%2==0){
					tempS=new String[clen/2];
				}else{
					int sLen=(clen/2)+1;
					tempS=new String[sLen];
					tempS[sLen-1]=""+tempChars[clen-1];
				}
				int count=0;
				for(int i=0;i<clen;i++){
					if(i%2!=0){
						tempS[count]=(tempChars[i])+""+(tempChars[i-1]);
						count++;
					}
				}
				int tempLen=tempS.length;
				if(tempLen>3){
					String huan=tempS[1];
					tempS[1]=tempS[tempLen-2];
					tempS[tempLen-2]=huan;
					huan=null;
				}
				
				String tempEStr="";
				for(int k=0;k<tempS.length;k++){
					tempEStr+=tempS[k];
				}
				
				encryptStr=changeNegativeLocation(tempEStr);
				
//				for(int m=0;m<key_1.length;m++){
//					encryptStr=encryptStr.replaceAll(key_1[m], key_2[m]);
//				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return encryptStr;
	}
	
	
	/**
	 * 十六进制加密2
	 * @param sourceStr 源字符串
	 * @return
	 */
    public static String encryptHex2(String sourceStr){
    	if(!sourceStr.equals("")){
	    	String tempStr=sourceStr;
	    	try{
	    		String temp=parseByte2HexStrReversion(sourceStr);
	    		String temp2=parseByte2HexStr(temp);
	    		tempStr=changeNegativeLocation(temp2).toUpperCase();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return tempStr;
    	}else{
    		return "";
    	}
    }
    
    
	/**
	 * 十六进制加密3
	 * @param sourceStr 源字符串
	 * @return
	 */
    public static String encryptHex3(String sourceStr){
    	if(!sourceStr.equals("")){
	    	String tempStr=sourceStr;
	    	try{
	    		String temp=parseByte2HexStr(sourceStr);
	    		StringBuffer sb=new StringBuffer(temp);
	    		temp=sb.reverse().toString();
	    		tempStr=changeNegativeLocation(temp).toUpperCase();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return tempStr;
    	}else{
    		return "";
    	}
    }
    
    
    /**
     * 十六进制解密
     * @param hexStr hexStr
     * @return
     */
    public static String decryptHex(String hexStr){
    	if(!hexStr.equals("")){
    		try{
    			
//				for(int m=0;m<key_1.length;m++){
//					hexStr=hexStr.replaceAll(key_2[m], key_1[m]);
//				}
	    		hexStr=changePositiveLocation(hexStr);
	    		
				char[] hexStrchars=hexStr.toCharArray();
	    		
				int clen=hexStrchars.length;
				String[] tempS=null;
				if(clen%2==0){
					tempS=new String[clen/2];
				}else{
					int sLen=(clen/2)+1;
					tempS=new String[sLen];
					tempS[sLen-1]=""+hexStrchars[clen-1];
				}
				
				
				
				int count=0;
				for(int i=0;i<clen;i++){
					if(i%2!=0){
						tempS[count]=(hexStrchars[i-1])+""+(hexStrchars[i]);
						count++;
					}
				}
	    		
				int tempLen=tempS.length;
				if(tempLen>3){
					String huan=tempS[1];
					tempS[1]=tempS[tempLen-2];
					tempS[tempLen-2]=huan;
					huan=null;
				}
				
	    		for(int j=0;j<tempS.length;j++){
	    			StringBuffer buffer=new StringBuffer(tempS[j]);
	    			tempS[j]=buffer.reverse().toString();
	    		}
				String tempEStr="";
				for(int k=0;k<tempS.length;k++){
					tempEStr+=tempS[k];
				}
				
				hexStr=parseHexStr2Byte(tempEStr);
				
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return hexStr;
    	}else{
    		return "";
    	}
    }
    

    /**
     * 十六进制解密2
     * @param hexStr hexStr
     * @return
     */
    public static String decryptHex2(String hexStr){
    	if(!hexStr.equals("")){
    		try{
	    		hexStr=changePositiveLocation(hexStr);
	    		hexStr=parseHexStr2Byte(hexStr);
	    		
	    		String[] tempHexStrs=hexStr.split("-");
	    		String tempHex="";
	    		for(int i=0;i<tempHexStrs.length;i++){
	    			StringBuffer sb=new StringBuffer(tempHexStrs[i]);
	    			tempHex+=sb.reverse();
	    		}
	    		hexStr=parseHexStr2Byte(tempHex);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return hexStr;
    	}else{
    		return "";
    	}
    }
    
 
    
    /**
     * 十六进制解密3
     * @param hexStr hexStr
     * @return
     */
    public static String decryptHex3(String hexStr){
    	if(!hexStr.equals("")){
    		try{
	    		hexStr=changePositiveLocation(hexStr);
	    		
	    		StringBuffer sbf=new StringBuffer(hexStr);
	    		hexStr=sbf.reverse().toString();
	    		
	    		hexStr=parseHexStr2Byte(hexStr);
	    		
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		return hexStr;
    	}else{
    		return "";
    	}
    }
    
    
    /**
     * 反方向旋转字符串
     * @param hexSoruceStr
     * @return
     */
    private static String changeNegativeLocation(String hexSoruceStr){
    	int len=hexSoruceStr.length();
    	int midIndex=len/2;
    	int extra=len%2;
    	if(extra==0){
    		//偶数
    		String leftStr=hexSoruceStr.substring(0,midIndex);
    		String rightStr=hexSoruceStr.substring(midIndex);
    		return rightStr+leftStr;
    	}else{
    		//基数
    		String leftStr=hexSoruceStr.substring(0,midIndex+1);
    		String rightStr=hexSoruceStr.substring(midIndex+1);
    		return rightStr+leftStr;
    	}

    }

    /**
     * 正方向旋转字符串
     * @param hexSoruceStr
     * @return
     */
    private static String changePositiveLocation(String hexSoruceStr){
    	int len=hexSoruceStr.length();
    	int midIndex=len/2;
		String leftStr=hexSoruceStr.substring(0,midIndex);
		String rightStr=hexSoruceStr.substring(midIndex);
		return rightStr+leftStr;
    }

    
	/**
	 * 将字符串转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	private static String parseByte2HexStr(String erStr) throws Exception{
			byte[] buf=erStr.getBytes("utf-8");
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                } 
	                sb.append(hex.toUpperCase());  
	        }
	        return sb.toString();  
	}
	
	
	/**
	 * 将字符串转换成16进制 带反转
	 * @param buf 
	 * @return 
	 */  
	private static String parseByte2HexStrReversion(String erStr) throws Exception{
			byte[] buf=erStr.getBytes("utf-8");
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }
	                StringBuffer sbr=new StringBuffer(hex);
	                if(i==0){
	                	sb.append(sbr.reverse()); 
	                }else{
	                	sb.append("-"+sbr.reverse());
	                }
	        }
	        return sb.toString();  
	}
	
	/**
	 * 将16进制转换为字符串
	 * @param hexStr 
	 * @return 
	 */  
	private static String parseHexStr2Byte(String hexStr)  throws Exception{  
	        if (hexStr.length() < 1)  
	                return null;
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return new String(result,"utf-8");  
	}
    
    /**
     * 打散字符串(暂时不用)
     * @param hexSoruceStr
     * @return
     */
    private static String appedKey(String hexSoruceStr,String key){
    	String tempStr=hexSoruceStr;
    	int len=hexSoruceStr.length();
    	int midIndex=len/2;
    	if(len<=10){
    		int mI=hexSoruceStr.indexOf(midIndex);
    		String leftStr=hexSoruceStr.substring(0,mI);
    		String rightStr=hexSoruceStr.substring(mI);
    		tempStr=rightStr+leftStr;
    		
    	}else{
    		int leftMidIndex=midIndex/2;
    		int rightMidIndex=midIndex+leftMidIndex;
    	}
    	return tempStr;
    }

}
