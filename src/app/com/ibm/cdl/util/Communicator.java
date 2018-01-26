package com.ibm.cdl.util;

import java.util.HashMap;

/**
 * Created on 2005-8-15
 *
 */
public interface Communicator {

        /**
         * 发送用XML描述的查询请求到OA server，得到用XML描述的响应
         *
         * @param xml
         * @return
         */
        public String communicate(String requrl,String reqxml) throws CommunicatorException;
        public String communicate(String xml) throws CommunicatorException;

        public void setUrl(String url);

        public String getUrl();

        /**
         * 以Http Post 方式发送key/value对
         *
         * @param h
         * @return
         * @throws CommunicatorException
         */
        public String communicate(HashMap h) throws CommunicatorException;
}
