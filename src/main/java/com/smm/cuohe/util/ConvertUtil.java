package com.smm.cuohe.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConvertUtil
{
  public static Map beanToMap(Object bean)
    throws IntrospectionException, IllegalAccessException, InvocationTargetException
  {
    Class type = bean.getClass();
    Map returnMap = new HashMap();

    BeanInfo beanInfo = Introspector.getBeanInfo(type);

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; i++) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (!propertyName.equals("class")) {
        Method readMethod = descriptor.getReadMethod();
        Object result = readMethod.invoke(bean, new Object[0]);
        if ((result != null) && (result != "")) {
          returnMap.put(propertyName, result);
        }
      }
    }
    return returnMap;
  }

  public static String beanToJson(Object obj)
  {
    ObjectMapper mapper = new ObjectMapper();
    String json = null;
    try {
      json = mapper.writeValueAsString(obj);
    }
    catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }

  public static Map jsonToMap(String json)
  {
    Map map = null;
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      map = (Map)mapper.readValue(json, HashMap.class);
    }
    catch (JsonParseException e) {
      e.printStackTrace();
    }
    catch (JsonMappingException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return map;
  }
  public static Object jsonToBean(String json, Class<?> t) {
    ObjectMapper mapper = new ObjectMapper();

    Object obj = null;
    try {
      obj = mapper.readValue(json, t);
    }
    catch (JsonParseException e) {
      e.printStackTrace();
    }
    catch (JsonMappingException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return obj;
  }

  public static Object mapToBean(Map mpFrom, Object objTo)
  {
    Object[] objKeys = mpFrom.keySet().toArray();
    String strFieldName = "";

      for (Object objkey : objKeys) {
    	    try
    	    {
        strFieldName = objkey.toString();

        Field objField = objTo.getClass().getDeclaredField(
          strFieldName);
        objField.setAccessible(true);

        objField.set(objTo, mpFrom.get(strFieldName).toString());
    	    } catch (Exception e) {
    	        continue;
    	      }
      }

    return objTo;
  }

  public static Object map2Bean(Map map, Class cls)
  {
    Object obj = null;
    try {
      obj = cls.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }

    Method[] methods = cls.getMethods();
    for (int i = 0; i < methods.length; i++)
    {
      String method = methods[i].getName();

      Class[] cc = methods[i].getParameterTypes();
      if (cc.length != 1)
      {
        continue;
      }

      if (method.indexOf("set") < 0) {
        continue;
      }
      String type = cc[0].getSimpleName();
      try
      {
        Object value = method.substring(3, 4).toLowerCase() + 
          method.substring(4);
        System.out.println("value == " + value);

        if ((!map.containsKey(value)) || (map.get(value) == null))
          continue;
        setValue(type, map.get(value), i, methods, obj);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return obj;
  }

  private static void setValue(String type, Object value, int i, Method[] method, Object bean)
  {
    if ((value != null) && (!value.equals("")))
      try {
        if (type.equals("String"))
        {
          method[i].invoke(bean, new Object[] { value });
        } else if ((type.equals("int")) || (type.equals("Integer"))) {
          method[i].invoke(bean, new Object[] { 
            new Integer((int) value) });
        } else if ((type.equals("double")) || (type.equals("Double"))) {
          method[i].invoke(bean, new Object[] { 
            new Double((double) value) });
        } else if ((type.equals("float")) || (type.equals("Float"))) {
          method[i].invoke(bean, new Object[] { 
            new Float((float) value) });
        } else if ((type.equals("long")) || (type.equals("Long"))) {
          method[i].invoke(bean, 
            new Object[] { new Long((long) value) });
        } else if ((type.equals("boolean")) || (type.equals("Boolean"))) {
          method[i].invoke(bean, new Object[] { Boolean.valueOf(
            (boolean) value) });
        } else if (type.equals("BigDecimal")) {
          method[i].invoke(bean, new Object[] { 
            new BigDecimal((char[]) value) });
        } else if (type.equals("Date")) {
          Date date = null;
          if (value.getClass().getName().equals("java.util.Date")) {
            date = (Date)value;
          } else {
            String format = ((String)value).indexOf(":") > 0 ? "yyyy-MM-dd HH:mm:ss" : 
              "yyyy-MM-dd";
            SimpleDateFormat sf = new SimpleDateFormat();
            sf.applyPattern(format);
            date = sf.parse((String)value);
          }
          if (date != null)
            method[i].invoke(bean, new Object[] { date });
        }
        else if (type.equals("byte[]")) {
          method[i].invoke(bean, 
            new Object[] { new String().getBytes() });
        }
      } catch (Exception e) {
        System.out
          .println("将linkHashMap 或 HashTable 里的值填充到javabean时出错,请检查!");
        e.printStackTrace();
      }
  }
  

}