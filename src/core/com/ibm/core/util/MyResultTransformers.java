package com.ibm.core.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.HibernateException;
import org.hibernate.lob.SerializableClob;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.AliasToBeanResultTransformer;

public class MyResultTransformers extends AliasToBeanResultTransformer{
	private final Class resultClass;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;
	public MyResultTransformers(Class resultClass) {
		super(resultClass);
		if(resultClass==null) throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] { PropertyAccessorFactory.getPropertyAccessor(resultClass,null), PropertyAccessorFactory.getPropertyAccessor("field")}); 
	}
	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		try {
			if(setters==null) {
				setters = new Setter[aliases.length];
				for (int i = 0; i < aliases.length; i++) {
					String alias = aliases[i];
					if(alias != null) {
						setters[i] = propertyAccessor.getSetter(resultClass, alias);
					}
				}
			}
			result = resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				if(setters[i]!=null) {
					if(tuple[i] != null && tuple[i] instanceof SerializableClob){
						setters[i].set(result, getClob((SerializableClob) tuple[i]), null);
					}else if(tuple[i] != null && tuple[i] instanceof java.sql.Date){
						setters[i].set(result, date2timestamp((Date) tuple[i]), null);
					}else if(tuple[i] != null && tuple[i] instanceof java.math.BigDecimal){
						setters[i].set(result, bigDecimal2long((BigDecimal) tuple[i]), null);
					}else{
						setters[i].set(result, tuple[i], null);
					}
				}
			}
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		} catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}
		return result;
	}
	public Timestamp date2timestamp(Date sqlDate){
		return new Timestamp(sqlDate.getTime());
	}
	public long bigDecimal2long(java.math.BigDecimal bigDecimal){
		return bigDecimal.longValue();
	}
	public String getClob(SerializableClob inTuple){
		Reader reader;
		StringBuffer sb = new StringBuffer();
		try {
			reader = inTuple.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			String temp = null;
			while ((temp=br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return sb.toString();
	}

}
