package com.kevin.monitor.core.config;

import com.kevin.monitor.core.utils.PropUtils;

import java.io.*;
import java.util.*;

/**
 * reference from tprofiler
 */
public class ConfigureProperties extends Properties {

  private static final long serialVersionUID = -3868173073422671544L;

  private Properties delegate;
  
  private Properties context;
  
  
  public ConfigureProperties(Properties delegate, Properties context) {
    super();
    this.delegate = delegate;
    this.context = context;
  }

  public Object setProperty(String key, String value) {
    return delegate.setProperty(key, value);
  }

  public void load(Reader reader) throws IOException {
    delegate.load(reader);
  }

  public int size() {
    return delegate.size();
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  public Enumeration<Object> keys() {
    return delegate.keys();
  }

  public Enumeration<Object> elements() {
    return delegate.elements();
  }

  public void load(InputStream inStream) throws IOException {
    delegate.load(inStream);
  }

  public boolean contains(Object value) {
    return delegate.contains(value);
  }

  public boolean containsValue(Object value) {
    return delegate.containsValue(value);
  }

  public boolean containsKey(Object key) {
    return delegate.containsKey(key);
  }

  public Object get(Object key) {
    return delegate.get(key);
  }

  public Object put(Object key, Object value) {
    return delegate.put(key, value);
  }

  public Object remove(Object key) {
    return delegate.remove(key);
  }

  public void putAll(Map<? extends Object, ? extends Object> t) {
    delegate.putAll(t);
  }

  public void clear() {
    delegate.clear();
  }

  public Object clone() {
    return delegate.clone();
  }

  public String toString() {
    return delegate.toString();
  }

  public Set<Object> keySet() {
    return delegate.keySet();
  }

  public Set<Map.Entry<Object, Object>> entrySet() {
    return delegate.entrySet();
  }

  public void save(OutputStream out, String comments) {
    delegate.save(out, comments);
  }

  public void store(Writer writer, String comments) throws IOException {
    delegate.store(writer, comments);
  }

  public Collection<Object> values() {
    return delegate.values();
  }

  public void store(OutputStream out, String comments) throws IOException {
    delegate.store(out, comments);
  }

  public boolean equals(Object o) {
    return delegate.equals(o);
  }

  public int hashCode() {
    return delegate.hashCode();
  }

  public void loadFromXML(InputStream in) throws IOException,
          InvalidPropertiesFormatException {
    delegate.loadFromXML(in);
  }

  public void storeToXML(OutputStream os, String comment) throws IOException {
    delegate.storeToXML(os, comment);
  }

  public void storeToXML(OutputStream os, String comment, String encoding)
    throws IOException {
    delegate.storeToXML(os, comment, encoding);
  }

  public String getProperty(String key) {
    String value = delegate.getProperty(key);
    return PropUtils.getPropValue(value, context);
  }

  public String getProperty(String key, String defaultValue) {
    String value = delegate.getProperty(key, defaultValue);
    return PropUtils.getPropValue(value, context);
  }

  public Enumeration<?> propertyNames() {
    return delegate.propertyNames();
  }

  public Set<String> stringPropertyNames() {
    return delegate.stringPropertyNames();
  }

  public void list(PrintStream out) {
    delegate.list(out);
  }

  public void list(PrintWriter out) {
    delegate.list(out);
  }
  
}
