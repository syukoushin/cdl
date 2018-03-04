/**
 * OclCarSalesBack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public class OclCarSalesBack  implements java.io.Serializable {
    private java.lang.String KCBZXX;

    private java.lang.String KCSXZT;

    private java.lang.String XSVINM;

    public OclCarSalesBack() {
    }

    public OclCarSalesBack(
           java.lang.String KCBZXX,
           java.lang.String KCSXZT,
           java.lang.String XSVINM) {
           this.KCBZXX = KCBZXX;
           this.KCSXZT = KCSXZT;
           this.XSVINM = XSVINM;
    }


    /**
     * Gets the KCBZXX value for this OclCarSalesBack.
     * 
     * @return KCBZXX
     */
    public java.lang.String getKCBZXX() {
        return KCBZXX;
    }


    /**
     * Sets the KCBZXX value for this OclCarSalesBack.
     * 
     * @param KCBZXX
     */
    public void setKCBZXX(java.lang.String KCBZXX) {
        this.KCBZXX = KCBZXX;
    }


    /**
     * Gets the KCSXZT value for this OclCarSalesBack.
     * 
     * @return KCSXZT
     */
    public java.lang.String getKCSXZT() {
        return KCSXZT;
    }


    /**
     * Sets the KCSXZT value for this OclCarSalesBack.
     * 
     * @param KCSXZT
     */
    public void setKCSXZT(java.lang.String KCSXZT) {
        this.KCSXZT = KCSXZT;
    }


    /**
     * Gets the XSVINM value for this OclCarSalesBack.
     * 
     * @return XSVINM
     */
    public java.lang.String getXSVINM() {
        return XSVINM;
    }


    /**
     * Sets the XSVINM value for this OclCarSalesBack.
     * 
     * @param XSVINM
     */
    public void setXSVINM(java.lang.String XSVINM) {
        this.XSVINM = XSVINM;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OclCarSalesBack)) return false;
        OclCarSalesBack other = (OclCarSalesBack) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.KCBZXX==null && other.getKCBZXX()==null) || 
             (this.KCBZXX!=null &&
              this.KCBZXX.equals(other.getKCBZXX()))) &&
            ((this.KCSXZT==null && other.getKCSXZT()==null) || 
             (this.KCSXZT!=null &&
              this.KCSXZT.equals(other.getKCSXZT()))) &&
            ((this.XSVINM==null && other.getXSVINM()==null) || 
             (this.XSVINM!=null &&
              this.XSVINM.equals(other.getXSVINM())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getKCBZXX() != null) {
            _hashCode += getKCBZXX().hashCode();
        }
        if (getKCSXZT() != null) {
            _hashCode += getKCSXZT().hashCode();
        }
        if (getXSVINM() != null) {
            _hashCode += getXSVINM().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OclCarSalesBack.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclCarSalesBack"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KCBZXX");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KCBZXX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KCSXZT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KCSXZT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XSVINM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XSVINM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
