/**
 * OclInvoiceAndDrivingLicenseBack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bjev.dms;

public class OclInvoiceAndDrivingLicenseBack  implements java.io.Serializable {
    private java.lang.String KCCSBZ;

    private java.lang.String KCCSZT;

    private java.lang.String XSVINM;

    public OclInvoiceAndDrivingLicenseBack() {
    }

    public OclInvoiceAndDrivingLicenseBack(
           java.lang.String KCCSBZ,
           java.lang.String KCCSZT,
           java.lang.String XSVINM) {
           this.KCCSBZ = KCCSBZ;
           this.KCCSZT = KCCSZT;
           this.XSVINM = XSVINM;
    }


    /**
     * Gets the KCCSBZ value for this OclInvoiceAndDrivingLicenseBack.
     * 
     * @return KCCSBZ
     */
    public java.lang.String getKCCSBZ() {
        return KCCSBZ;
    }


    /**
     * Sets the KCCSBZ value for this OclInvoiceAndDrivingLicenseBack.
     * 
     * @param KCCSBZ
     */
    public void setKCCSBZ(java.lang.String KCCSBZ) {
        this.KCCSBZ = KCCSBZ;
    }


    /**
     * Gets the KCCSZT value for this OclInvoiceAndDrivingLicenseBack.
     * 
     * @return KCCSZT
     */
    public java.lang.String getKCCSZT() {
        return KCCSZT;
    }


    /**
     * Sets the KCCSZT value for this OclInvoiceAndDrivingLicenseBack.
     * 
     * @param KCCSZT
     */
    public void setKCCSZT(java.lang.String KCCSZT) {
        this.KCCSZT = KCCSZT;
    }


    /**
     * Gets the XSVINM value for this OclInvoiceAndDrivingLicenseBack.
     * 
     * @return XSVINM
     */
    public java.lang.String getXSVINM() {
        return XSVINM;
    }


    /**
     * Sets the XSVINM value for this OclInvoiceAndDrivingLicenseBack.
     * 
     * @param XSVINM
     */
    public void setXSVINM(java.lang.String XSVINM) {
        this.XSVINM = XSVINM;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OclInvoiceAndDrivingLicenseBack)) return false;
        OclInvoiceAndDrivingLicenseBack other = (OclInvoiceAndDrivingLicenseBack) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.KCCSBZ==null && other.getKCCSBZ()==null) || 
             (this.KCCSBZ!=null &&
              this.KCCSBZ.equals(other.getKCCSBZ()))) &&
            ((this.KCCSZT==null && other.getKCCSZT()==null) || 
             (this.KCCSZT!=null &&
              this.KCCSZT.equals(other.getKCCSZT()))) &&
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
        if (getKCCSBZ() != null) {
            _hashCode += getKCCSBZ().hashCode();
        }
        if (getKCCSZT() != null) {
            _hashCode += getKCCSZT().hashCode();
        }
        if (getXSVINM() != null) {
            _hashCode += getXSVINM().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OclInvoiceAndDrivingLicenseBack.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dms.bjev.com", "oclInvoiceAndDrivingLicenseBack"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KCCSBZ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KCCSBZ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("KCCSZT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "KCCSZT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XSVINM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XSVINM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
