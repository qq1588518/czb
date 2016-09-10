/*
 * CXML.java
 *
 * Created on 2007年7月17日, 下午2:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.ztenc.oa.proj.util;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.xpath.XPath;
import org.jdom.transform.*;
import java.util.*;
import java.io.*;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
/**
 *
 * @author heyh
 */
public class CXML{
    
	
	private static final String  ROOT = "zygj";
    /**根元素对象*/
    private Element root = new Element(ROOT);
    
    /**默认的XMLDocument对象*/
    private Document document = new Document(root);
    
    /**保存xpath路径*/
    private StringBuffer based_on_xpath = null;
    
    /**保存根路径*/
    private String xpath_root = "";
    
    //private static TrasformObject transformobj;
    
    /** Creates a new instance of CXML */
    public CXML() {
        initXPathRoot();
    }
    
    /**带xml的构造器，初始化就解析成文档*/
    public CXML(String xml) throws Exception {
        initXPathRoot();
        forXML(xml);
    }
    
    /**进行CXML对象的初始化操作，初始化根路径为/hyh,创建一个默认路径下并且只有根元素的document对象,私有方法*/
    private void initMember() {
        initXPathRoot();
        createDefaultXMLDocument();
    }
    
    /**用来重置CXM对象的状态，恢复到对象的初始化的值*/
    public void reset() {
        initMember();
    }
    
    public void resetRelativeXPath() {
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append("/");
        based_on_xpath.append(ROOT);
    }
    
    /**初始化根路径为/hyh,私有方法*/
    private void initXPathRoot() {
        based_on_xpath = new StringBuffer("/");
        based_on_xpath.append(ROOT);
        xpath_root = based_on_xpath.toString();
    }
    
    /**设置文档对象的根元素,参数为ELement*/
    public void setRootElement(Element _root) {
        root = _root;
        document.setRootElement(_root);
    }
    
    /**设置文档对象的根元素,参数为String*/
    public void setRootElement(String _root) {
        root = new Element(_root);
        document.setRootElement(root);
    }
    
    /**获得跟元素对象*/
    
    public Element getRootElement() {
        return root;
    }
    
    /**获得根元素名称*/
    public String getRootElementName() {
        return root.getName();
    }
    
    /**创建一个默认路径下并且只有根元素的XMLDocument对象,私有方法*/
    private void createDefaultXMLDocument() {
        root = new Element(ROOT);
        document = new Document(root);
    }
    
    /**创建一个默认的XMLDocument对象并把对象赋给本对象,私有方法*/
    private void createDefaultXMLDocument(String xml) throws Exception {
        Document _document = getDocumentByXML(xml);
        Element _root = _document.getRootElement();
        if(_root.getName().equals(ROOT)) {
            root = _root;
            document = _document;
        }else {
            throw new Exception("根节点不符合系统格式");
        }
    }
    
    private void createDefaultXMLDocument(java.io.InputStream is) throws Exception {
        Document _document = getDocumentByXML(is);
        Element _root = _document.getRootElement();
        if(_root.getName().equals(ROOT)) {
            root = _root;
            document = _document;
        }else {
            throw new Exception("根节点不符合系统格式");
        }
    }
    
    /**在当前元素下添加一个node节点,参数为Element _root,Elemeng node*/
    public void addNode(Element _root,Element node) {
        _root.addContent(node);
    }
    
    /**在当前元素下添加一个node节点,参数为Element _root,String node*/
    public void addNode(Element _root,String node) {
        Element element = new Element(node);
        _root.addContent(element);
    }
    
    public void addNode(String node) {
        Element element = new Element(node);
        root.addContent(element);
    }
    
    /**在指定路径下添加一个相对节点,参数为Element*/
    public Element addRelativeNode(Element node) throws Exception{
        return addNodeByXPath(based_on_xpath.toString(),node.getName());
    }
    
    /**在指定路径下添加一个相对节点,参数为String node*/
    public Element addRelativeNode(String node) throws Exception{
        return addNodeByXPath(based_on_xpath.toString(),node);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,String node_text*/
    public Element addRelativeNode(String node,String node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,int node_text*/
    public Element addRelativeNode(String node,int node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,boolean node_text*/
    public Element addRelativeNode(String node,boolean node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,float node_text*/
    public Element addRelativeNode(String node,float node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,double node_text*/
    public Element addRelativeNode(String node,double node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,double node_text*/
    public Element addRelativeNode(String node,java.sql.Timestamp node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,double node_text*/
    public Element addRelativeNode(String node,byte[] node_text) throws Exception {
        return addNodeByXPath(based_on_xpath.toString(),node,node_text);
    }
    
    /**在指定路径下添加一个相对节点和节点值,参数为Element node,double node_text*/
    public void addRelativeNode(Hashtable ht) throws Exception {
        addNodeByXPath(based_on_xpath.toString(),ht);
    }
    
    /**在指定路径下添加相对节点和节点值,节点及节点值是以element对象下指定的 children_xpath路径下的所有元素*/
    public void addRelativeNode(Element element,String children_xpath) throws Exception {
        addNodeByXPath(based_on_xpath.toString(),element,children_xpath);
    }
    
    /**在指定路径下添加相对节点和节点值,节点及节点值是以xml对象下指定的 children_xpath路径下的所有元素*/
    public void addRelativeNode(CXML xml,String children_xpath) throws Exception {
        Document _document = xml.getDocment();
        addNodeByXPath(based_on_xpath.toString(),xml,_document,children_xpath);
    }
    
    /**根据相对路径添加节点,这里默认相对路径为当前based_on_xpath路径*/
    public Element addNodeByRelativeXPath(String node_name) throws Exception{
        return addNodeByXPath(based_on_xpath.toString(),node_name);
    }
    
    /**根据相对路径添加节点,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,String node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,int node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,boolean node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,byte[] node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,float node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,double node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public void addNodeByRelativeXPath(String xpath,Hashtable ht) throws Exception {
        addNodeByXPath(getRelativeXPath(xpath),ht);
    }
    
    /**根据相对路径添加节点及节点值,这里的相对路径是传入的XPath参数*/
    public Element addNodeByRelativeXPath(String xpath,String node_name,java.sql.Timestamp node_text) throws Exception {
        return addNodeByXPath(getRelativeXPath(xpath),node_name,node_text);
    }
    
    /**根据相对路径添家节点及节点值,这里的相对路径是传入的XPath参数,节点急节点值是以指定的element对象为环境的children_xpath下的所有元素*/
    public void addNodeByRelativeXPath(String xpath,Element element,String children_xpath) throws Exception {
        addNodeByXPath(getRelativeXPath(xpath),element,children_xpath);
    }
    
    /**在指定路径下添加相对节点和节点值,节点及节点值是以xml对象下指定的 children_xpath路径下的所有元素*/
    public void addNodeByRelativeXPath(String xpath,CXML xml,String children_xpath) throws Exception {
        Document _document = xml.getDocment();
        addNodeByXPath(getRelativeXPath(xpath),xml,_document,children_xpath);
    }
    
    /**在根节点上添加新节点，且指针位置不变*/
    public Element addAbsoluteNode(String node_name)throws Exception{
        return addNodeByXPath(xpath_root,node_name);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,String node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,String node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,int node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,int node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,boolean node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,boolean node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,float node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,float node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,double node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,double node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,byte[] node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,byte[] node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为String node,java.sql.timestamp node_text,且指针位置不变*/
    public Element addAbsoluteNode(String node_name,java.sql.Timestamp node_text)throws Exception{
        return addNodeByXPath(xpath_root,node_name,node_text);
    }
    
    /**在根节点上添加新节点及节点值，参数为Hashtable ht,且指针位置不变*/
    public void addAbsoluteNode(Hashtable ht)throws Exception{
        addNodeByXPath(xpath_root,ht);
    }
    
    /**在根节点上添加新节点及节点值,节点是以指定的elemnt对象下的children_xpath的所有元素,参数为Element element,String children_xpath*/
    public void addAbsoluteNode(Element element,String children_xpath) throws Exception {
        addNodeByXPath(xpath_root,element,children_xpath);
    }
    
    public void addAbsoluteNode(CXML xml,String children_xpath) throws Exception {
        Document _document = xml.getDocment();
        addNodeByXPath(xpath_root,xml,_document,children_xpath);
    }
    
    /**根据路径添加节点和节点值*/
    public Element addNodeByXPath(String xpath,String node_name,String node_text) throws Exception {
        Element parent = getSpecialElement(xpath);
        Element element = new Element(node_name);
        element.setText(node_text);
        parent.addContent(element);
        return element;
    }
    
    /**根据路径添加节点和节点值,参数String xpath,String node_name,int node_text*/
    private Element addNodeByXPath(String xpath,String node_name,int node_text) throws Exception {
        String rtn = String.valueOf(node_text);
        Element element = addNodeByXPath(xpath,node_name,rtn);
        return element;
    }
    
    /**根据路径添加节点和节点值,参数String xpath,String node_name,boolean node_text*/
    private Element addNodeByXPath(String xpath,String node_name,boolean node_text) throws Exception {
        String rtn = "";
        if(node_text) {
            rtn = String.valueOf(enumBooleanType.TRUE);
        }else {
            rtn = String.valueOf(enumBooleanType.FALSE);
        }
        Element element = addNodeByXPath(xpath,node_name,rtn);
        return element;
    }
    /**根据路径添加节点和节点值,参数String xpath,String node_name,float node_text*/
    private Element addNodeByXPath(String xpath,String node_name,float node_text) throws Exception {
        String rtn = String.valueOf(node_text);
        Element element = addNodeByXPath(xpath,node_name,rtn);
        return element;
    }
    
    /**根据路径添加节点和节点值,参数String xpath,String node_name,double node_text*/
    private Element addNodeByXPath(String xpath,String node_name,double node_text) throws Exception {
        String rtn = String.valueOf(node_text);
        Element element = addNodeByXPath(xpath,node_name,rtn);
        return element;
    }
    
    /**根据路径添加节点和节点值,参数String xpath,String node_name,Timestamp node_text*/
    private Element addNodeByXPath(String xpath,String node_name,java.sql.Timestamp node_text) throws Exception {
        String rtn = "";
        if(node_text !=null) {
            rtn = node_text.toString();
        }
        Element element = addNodeByXPath(xpath,node_name,rtn);
        return element;
    }
    
    /**根据路径添加节点和节点值,参数String xpath,String node_name,byte[] node_text*/
    private Element addNodeByXPath(String xpath,String node_name,byte[] node_text) throws Exception {
        String rtn = "";
        if(node_text !=null) {
            rtn = toHexString(node_text);
        }
        Element element = addNodeByXPath(xpath,node_name,rtn);
        return element;
    }
    
    /**根据路径添加节点,参数String xpath,String node_name*/
    private Element addNodeByXPath(String xpath,String node_name) throws Exception{
        Element element = getSpecialElement(xpath);
        element.addContent(node_name);
        return element;
    }
    
    /**根据路径添加节点,参数String xpath,Hashtable ht*/
    private void addNodeByXPath(String xpath,Hashtable ht) throws Exception{
        String key = "";
        String value = "";
        Element element = null;
        Enumeration e = ht.keys();
        while(e.hasMoreElements()) {
            key = (String)e.nextElement();
            value = (String)ht.get(key);
            addNodeByXPath(xpath,key,value);
        }
    }
    
    /**在指定的xpath路径下添加element元素及所有子元素*/
    private Element addNodeByXPath(String xpath,Element element) throws Exception {
        Element root = (Element)getSpecialElement(xpath);
        Element ele = (Element)element.clone();
        return (Element)root.addContent(ele);
    }
    
    public Element insertNodeByXPath(String xpath,String ele,int index) throws Exception {
        Element element = new Element(ele);
        Element root = (Element)getSpecialElement(xpath);
        Element tmp_element = (Element)element.clone();
        root.addContent(index,tmp_element);
        return tmp_element;
    }
    
    public Element insertNodeByXPath(String xpath,Element element,int index) throws Exception {
        Element root = (Element)getSpecialElement(xpath);
        Element ele = (Element)element.clone();
        root.addContent(index,ele);
        return ele;
    }
    
    public Element insertRelativeNodeByXPath(Element element,int index) throws Exception {
        Element root = (Element)getSpecialElement(based_on_xpath.toString());
        Element ele = (Element)element.clone();
        return (Element)root.addContent(index,ele);
    }
        
    /**
     *在指定的节点xpath下添加element元素指定的children_xpath下的所有元素
     *例如xml字符串为:<hyh><tt><bb>123</bb></tt><cc><dd>345</dd></cc></hyh>
     *那么addNodeByXPath("hyh/tt",cc,"/cc")就是将<cc>下所有的子节点添加到/hyh/tt下
     *结果:<hyh><tt><bb>123</bb></dd></tt><cc><dd>345</dd></cc></hyh>    
     *注：element一般是根节点
     */
    private void addNodeByXPath(String xpath,Element element,String children_xpath) throws Exception {
        List list = getElements(children_xpath);
        java.util.Iterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element tmp_ele = (Element)iterator.next();
            addNodeByXPath(xpath,tmp_ele);
        }
    }
    
    /**在指定的xpath路径下添加以xml对象指定的children_xpath下的所有元素*/
    private void addNodeByXPath(String xpath,CXML xml,Document _document,String children_xpath) throws Exception {
        List list = xml.getElements(_document,children_xpath);
        java.util.Iterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element tmp_ele = (Element)iterator.next();
            addNodeByXPath(xpath,tmp_ele);
        }
    }
    
    /**
     *在指定的xpath路径下添加一个xml文档字符串
     *首先判断当前字符串是否格式正确,有没有根元素，
     *解析自定义的xml文档字符串然后在根据xpath添加xml文档字符串下的所有元素
     */
    public void addNodeByXML(String xpath,String xml) throws Exception {
        if(!CXML.hasRootElement(xml)) {
            throw new Exception("xml字符串没有根节点.");
        }else {
            CXML cxml = new CXML();
            StringBuffer sb_xpath = new StringBuffer("/");
            Document _document = cxml.forCustomXML(xml);
            sb_xpath.append(_document.getRootElement().getName());
            addNodeByXPath(xpath,cxml,_document,sb_xpath.toString());
        }
    }
    
    /**根据相对路径添加xml文档下的所有元素*/
    public void addNodeByXMLRelative(String xml) throws Exception {
        addNodeByXML(based_on_xpath.toString(),xml);
    }
    
    public boolean removeNode(Element element) throws Exception {
        boolean rtn = false;
        Element parent_element = (Element)element.getParent();
        //rtn = parent_element.removeChildren(element.getName());
        rtn = parent_element.removeChild(element.getName());
        return rtn;
    }
    
    public boolean removeNode(String xpath) throws Exception {
        boolean rtn = false;
        Element element = getSpecialElement(xpath);
        rtn = removeNode(element);
        return rtn;
    }
    
    public boolean removeRelativeNode() throws Exception {
        return removeNode(based_on_xpath.toString());
    }
    
    public boolean removeAllChildNode(Element parent_element) throws Exception {
        boolean rtn = false;
        List list = parent_element.getChildren();
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element child_element = (Element)iterator.next();
            rtn =removeNode(child_element);
            if(!rtn) {
                break;
            }
        }
        return rtn;
    }
    
    public boolean removeAllChildNode(String xpath) throws Exception {
        boolean rtn = false;
        Element parent_element = getSpecialElement(xpath);
        rtn = removeAllChildNode(parent_element);
        return rtn;
    }
    
    public boolean removeAllRelativeChildNode() throws Exception {
        return removeAllChildNode(based_on_xpath.toString());
    }
    
    public boolean replaceNode(Element old_element,Element new_element) throws Exception {
        boolean rtn = false;
        Element parent_element = (Element)old_element.getParent();
        int index = getElementPosition(old_element,parent_element);
        String tmp_xpath = getRelativeXPathByElement(old_element);
        List list = old_element.cloneContent();
        rtn = removeNode(old_element);
        Element new_parent_element = insertNodeByXPath(tmp_xpath,new_element,index);
        if(!rtn) {
            return rtn;
        }
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element child_element = (Element)iterator.next();
            Element tmp_element = (Element)child_element.clone();
            addNode(new_parent_element,tmp_element);
        }
        
        return rtn;
    }
    
    public boolean replaceNode(Element old_element,String new_element) throws Exception {
        boolean rtn = false;
        Element parent_element = (Element)old_element.getParent();
        int index = getElementPosition(old_element,parent_element);
        String tmp_xpath = getRelativeXPathByElement(old_element);
        List list = old_element.cloneContent();
        Element new_parent_element = insertNodeByXPath(tmp_xpath,new_element,index);
        rtn = removeNode(old_element);
        
        if(!rtn) {
            return rtn;
        }
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element child_element = (Element)iterator.next();
            Element tmp_element = (Element)child_element.clone();
            addNode(new_parent_element,tmp_element);
        }
        
        return rtn;
    }
    
    public boolean replaceNode(String xpath,Element new_element) throws Exception {
        Element old_element = getSpecialElement(xpath);
        return replaceNode(old_element,new_element);
    }
    
    public boolean replaceNode(String xpath,String new_element) throws Exception {
        Element old_element = getSpecialElement(xpath);
        return replaceNode(old_element,new_element);
    }
    
    public boolean replaceRelativeNode(Element new_element) throws Exception {
        Element old_element = getSpecialElement(based_on_xpath.toString());
        return replaceNode(old_element,new_element);
    }
    
    public int getElementPosition(Element child_element,Element parent_element) throws Exception {
        int index = -1;
        List list = parent_element.getChildren();
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element tmp_element = (Element)iterator.next();
            if(tmp_element.equals(child_element)) {
                index = iterator.nextIndex();
                return index;
            }
        }
        return index;
    }
    
    public boolean replaceAllNode(Element old_element,Element new_element) throws Exception {
        boolean rtn = false;
        String tmp_xpath = getRelativeXPathByElement(old_element);
        Element parent_element = (Element)old_element.getParent();
        Element tmp_element = (Element)new_element.clone();
        rtn = removeAllChildNode(parent_element);
        addNodeByXPath(tmp_xpath,new_element);
        return rtn;
    }
    
    public boolean replaceAllNode(String xpath,Element new_element) throws Exception {
        boolean rtn = false;
        Element old_element = getSpecialElement(xpath);
        replaceAllNode(old_element,new_element);
        return rtn;
    }
    
    /**设置指定element元素的属性及属性值,参数为String xpath,String attr_name,String attr_value*/
    public Element setNodeAttribute(Element element,String attr_name,String attr_value) throws Exception {
        Element rtn = null;
        rtn = element.setAttribute(attr_name,attr_value);
        return rtn;
    }
    
    /**设置指定element元素的属性及属性值,参数为String xpath,String attr_name,int attr_value*/
    public Element setNodeAttribute(Element element,String attr_name,int attr_value) throws Exception {
        Element rtn = null;
        rtn = element.setAttribute(attr_name,String.valueOf(attr_value));
        return rtn;
    }
    
    /**添加指定xpath路径下元素的属性及属性值,参数为String xpath,String attr_name,String attr_value*/
    public Element setNodeAttribute(String xpath,String attr_name,String attr_value) throws Exception {
        Element element = getSpecialElement(xpath);
        element.setAttribute(attr_name,attr_value);
        return element;
    } 
    
    /**添加相对路径下元素的属性及属性值,参数为String xpath,String attr_name,String attr_value*/
    public Element setRelativeNodeAttribute(String attr_name,String attr_value) throws Exception {
        return setNodeAttribute(based_on_xpath.toString(),attr_name,attr_value);
    }
    
    /**添加指定xpath路径下元素的属性及属性值,参数为String xpath,String attr_name,int attr_value*/
    public Element setNodeAttribute(String xpath,String attr_name,int attr_value) throws Exception {
        Element element = getSpecialElement(xpath);
        element.setAttribute(attr_name,String.valueOf(attr_value));
        return element;
    } 
    
    /**添加相对路径下元素的属性及属性值,参数为String xpath,String attr_name,int attr_value*/
    public Element setRelativeNodeAttribute(String attr_name,int attr_value) throws Exception {
        return setNodeAttribute(based_on_xpath.toString(),attr_name,attr_value);
    }
    
    public void setNodeAttributes(String xpath,Hashtable ht) throws Exception {
        Element element = getSpecialElement(xpath);
        Enumeration enumer = ht.keys();
        while(enumer.hasMoreElements()) {
            String key = (String)enumer.nextElement();
            String value = (String)ht.get(key);
            setNodeAttribute(xpath,key,value);
        }
    }
    
    /**添加xml下指定xpath的所有元素的属性及属性值,参数为String xpath,CXML xml,String attr_name,String attr_value*/
    public void setNodeAttributes(String xpath,CXML xml,String attr_name,String attr_value) throws Exception {
        Document _document = xml.getDocment();
        String tmp_xpath = xpath.substring(0,2);
        if(!tmp_xpath.equals("//")) {
            throw new Exception("路径格式错误,应该是//加路径名");
        }
        List list = this.getElements(_document,xpath);
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element element = (Element)iterator.next();
            element.setAttribute(attr_name,attr_value);
        }
    }
    
    /**添加xml下相对路径的所有元素的属性及属性值,参数为String xpath,CXML xml,String attr_name,String attr_value*/
    public void setRelativeNodeAttributes(CXML xml,String attr_name,String attr_value) throws Exception {
        setNodeAttributes(based_on_xpath.toString(),xml,attr_name,attr_value);
    }
    
    /**添加xml下指定xpath的所有元素的属性及属性值,参数为String xpath,CXML xml,String attr_name,int attr_value*/
    public void setNodeAttributes(String xpath,CXML xml,String attr_name,int attr_value) throws Exception {
        setNodeAttributes(xpath,xml,attr_name,String.valueOf(attr_value));
    }
    
    public void setRelativeNodeAttributes(CXML xml,String attr_name,int attr_value) throws Exception {
        setNodeAttributes(based_on_xpath.toString(),xml,attr_name,String.valueOf(attr_value));
    }
    
    /**添加xml下所有元素的属性及属性值,参数为String xpath,CXML xml,String attr_name,String attr_value*/
    public void setNodeAttributes(CXML xml,String attr_name,String attr_value) throws Exception {
        Document _document = xml.getDocment();
        String xpath = "//";
        List list = getElements(_document,xpath);
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            if(iterator.next() instanceof org.jdom.Element) {
                iterator.previous();
                Element element = (Element)iterator.next();
                if(!element.getName().equals(ROOT) && iterator.nextIndex() !=2) {
                    element.setAttribute(attr_name,attr_value);
                }
            }
        }
    }
    
    /**添加xml下所有元素的属性及属性值,参数为String xpath,CXML xml,String attr_name,int attr_value*/
    public void setNodeAttributes(CXML xml,String attr_name,int attr_value) throws Exception {
        setNodeAttributes(xml,attr_name,String.valueOf(attr_value));
    }
    
    /**添加xml下的所有元素的属性及属性值,参数为CXML xml,Hashtable ht*/
    public void setNodeAttributes(CXML xml,Hashtable ht) throws Exception {
        Enumeration enumer = ht.keys();
        while(enumer.hasMoreElements()) {
            String key = (String)enumer.nextElement();
            String value = (String)ht.get(key);
            setNodeAttributes(xml,key,value);
        }
    }
    
    /**添加xml下指定xpath的所有元素的属性及属性值,参数为String xpath,CXML xml,Hashtable ht*/
    public void setNodeAttributes(String xpath,CXML xml,Hashtable ht) throws Exception {
        Enumeration enumer = ht.keys();
        while(enumer.hasMoreElements()) {
            String key = (String)enumer.nextElement();
            String value = (String)ht.get(key);
            setNodeAttributes(xpath,xml,key,value);
        }
    }
    
    /**添加xml下指定xpath的所有元素的属性及属性值,参数为String xpath,CXML xml,Hashtable ht*/
    public void setRelativeNodeAttributes(CXML xml,Hashtable ht) throws Exception {
        setNodeAttributes(based_on_xpath.toString(),xml,ht);
    }
    
    public void setNodeAttribute(Element element,Hashtable ht) throws Exception {
        Enumeration enumer = ht.keys();
        while(enumer.hasMoreElements()) {
            String key = (String)enumer.nextElement();
            String value = (String)ht.get(key);
            setNodeAttribute(element,key,value);
        }
    }
    
    /**获得指定元素的所有属性以及属性值*/
    public String getNodeAttributes(Element _element) throws Exception {
        StringBuffer sb = new StringBuffer();
        List list = _element.getAttributes();
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Attribute attr = (Attribute)iterator.next();
            String name = attr.getName();
            String value = attr.getValue();
            sb.append(" ");
            sb.append(name);
            sb.append("=");
            sb.append(value);
            if(iterator.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
    
    /**获得指定元素的所有属性以及属性值*/
    public String getNodeAttributes(String xpath) throws Exception {
        Element element = getSpecialElement(xpath);
        String rtn = getNodeAttributes(element);
        return rtn;
    }
    
    /**获得指定元素的所有属性以及属性值*/
    public String getRelativeNodeAttributes() throws Exception {
        Element element = getSpecialElement(based_on_xpath.toString());
        String rtn = getNodeAttributes(element);
        return rtn;
    }
    
    public String getNodeAttribute(Element element,String name) throws Exception {
        return element.getAttributeValue(name);
    }
    
    public String getNodeAttribute(String xpath,String name) throws Exception {
        Element element = getSpecialElement(xpath);
        String rtn = getNodeAttribute(element,name);
        return rtn;
    }
    
    public String getRelabtiveNodeAttribute(String name) throws Exception {
        Element element = getSpecialElement(based_on_xpath.toString());
        String rtn = getNodeAttribute(element,name);
        return rtn;
    }
    
    public boolean removeNodeAttributes(Element element) throws Exception {
        boolean rtn = true;
        List list = element.getAttributes();
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Attribute attr = (Attribute)iterator.next();
            rtn = element.removeAttribute(attr);
            if(!rtn) {
                break;
            }
        }
        return rtn;
    }
    
    public boolean removeNodeAttributes(String xpath) throws Exception {
        boolean rtn = true;
        Element element = getSpecialElement(xpath);
        rtn = removeNodeAttributes(element);
        return rtn;
    }
    
    public boolean removeRelativeNodeAttributes() throws Exception {
        return removeNodeAttributes(based_on_xpath.toString());
        
    }
    
    public boolean removeNodeAttribute(Element element,String attr_name) throws Exception {
        boolean rtn = element.removeAttribute(attr_name);
        return rtn;
    }
    
    public boolean removeNodeAttribute(String xpath,String attr_name) throws Exception {
        Element element = getSpecialElement(xpath);
        boolean rtn = removeNodeAttribute(element,attr_name);
        return rtn;
    }
    
    public boolean removeRelativeNodeAttribute(String attr_name) throws Exception {
        return removeNodeAttribute(based_on_xpath.toString(),attr_name);
    }
    
    public boolean replaceNodeAttributes(String xpath,Hashtable ht) throws Exception {
        boolean rtn = false;
        rtn = removeNodeAttributes(xpath);
        if(rtn) {
            setNodeAttributes(xpath,ht);
        }
        return rtn;
    }
    
    public boolean replaceNodeAttribute(Element element,String old_attr_name,String new_attr_name,String new_attr_value) throws Exception {
        boolean rtn = false;
        rtn = removeNodeAttribute(element,old_attr_name);
        if(rtn) {
            setNodeAttribute(element,new_attr_name,new_attr_value);
        }
        return rtn;
    }
    
    public boolean replaceNodeAttribute(String xpath,String old_attr_name,String new_attr_name,String new_attr_value) throws Exception {
        Element element = getSpecialElement(xpath);
        return replaceNodeAttribute(element,old_attr_name,new_attr_name,new_attr_value);
    }
    
    public boolean replaceRelativeNodeAttribute(String old_attr_name,String new_attr_name,String new_attr_value) throws Exception {
        Element element = getSpecialElement(based_on_xpath.toString());
        return replaceNodeAttribute(element,old_attr_name,new_attr_name,new_attr_value);
    }
    
    public void setNodeText(Element element,String node_text) {
        element.setText(node_text);
    }
    
    public void setNodeText(Element element,int node_text) {
        element.setText(String.valueOf(node_text));
    }
    
    public void setNodeText(String xpath,String node_text) throws Exception{
        Element element = getSpecialElement(xpath);
        setNodeText(element,node_text);
    }
    
    public void setNodeText(String xpath,int node_text) throws Exception{
        Element element = getSpecialElement(xpath);
        setNodeText(element,String.valueOf(node_text));
    }
    
    public void setRelativeNodeText(String node_text) throws Exception{
        Element element = getSpecialElement(based_on_xpath.toString());
        setNodeText(element,node_text);
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,String value*/
    public Element addNodeAndAttr(String node,String attr,String value) throws Exception{
        Element ele = addAbsoluteNode(node);
        setNodeAttribute(ele,attr,value);
        return ele;
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,int value*/
    public Element addNodeAndAttr(String node,String attr,int value) throws Exception{
        Element ele = addAbsoluteNode(node);
        setNodeAttribute(ele,attr,value);
        return ele;
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,boolean value*/
    public Element addNodeAndAttr(String node,String attr,boolean value) throws Exception{
        String rtn = "";
        if(value) {
            rtn = String.valueOf(enumBooleanType.TRUE);
        }else {
            rtn = String.valueOf(enumBooleanType.FALSE);
        }
        return addNodeAndAttr(node,attr,rtn);
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,float value*/
    public Element addNodeAndAttr(String node,String attr,float value) throws Exception{
        return addNodeAndAttr(node,attr,String.valueOf(value));
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,double value*/
    public Element addNodeAndAttr(String node,String attr,double value) throws Exception{
        return addNodeAndAttr(node,attr,String.valueOf(value));
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,Timestamp value*/
    public Element addNodeAndAttr(String node,String attr,java.sql.Timestamp value) throws Exception{
        return addNodeAndAttr(node,attr,String.valueOf(value));
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,String attr,byte[] value*/
    public Element addNodeAndAttr(String node,String attr,byte[] value) throws Exception{
        return addNodeAndAttr(node,attr,toHexString(value));
    }
    
    /**在绝对路径下添加一个节点和属性值,参数为String node,Hashtable ht*/
    public Element addNodeAndAttr(String node,Hashtable ht) throws Exception{
        Element ele = addAbsoluteNode(node);
        setNodeAttribute(ele,ht);
        return ele;
    }
    

	/**在指定路径下添加节点和值及属性和属性值,属性值封装为hashtable*/
	public Element addNodeAndAttrByXPath(String xpath,String node_name,String node_text,Hashtable ht) throws Exception{
        Element ele = addNodeByXPath(xpath,node_name,node_text);
        setNodeAttribute(ele,ht);
        return ele;
    }
    
	/**在指定路径下添加节点和值及属性和属性值*/
    public Element addNodeAndAttrByXPath(String xpath,String node_name,String node_text,String attr_name,String attr_value) throws Exception {
        Element element = addNodeByXPath(xpath,node_name,node_text);
        setNodeAttribute(element,attr_name,attr_value);
        return element;
    }
    
    public Element addNodeAndAttrByXPath(String xpath,String node_name,String node_text,String attr_name,int attr_value) throws Exception {
        Element element = addNodeByXPath(xpath,node_name,node_text);
        setNodeAttribute(element,attr_name,attr_value);
        return element;
    }

    /**在相对路径下添加节点和值及属性和属性值*/
    public Element addRelativeNodeAndAttrByXPath(String node_name,String node_text,String attr_name,String attr_value) throws Exception {
        return addNodeAndAttrByXPath(based_on_xpath.toString(),node_name,node_text,attr_name,attr_value);
    }
    
    /**在相对路径下的指定xpath下添加节点和值及属性和属性值*/
    public Element addNodeAndAttrByRelativeXPath(String xpath,String node_name,String node_text,String attr_name,String attr_value) throws Exception {
        return addNodeAndAttrByXPath(getRelativeXPath(xpath),node_name,node_text,attr_name,attr_value);
    }
    
    /**得到以element对象为根节点下的xpath路径下的所有元素,即一个List对象*/
    private List getElement(Element element,String xpath) throws Exception {
        List list = XPath.selectNodes(root,xpath);
        return list;
    }
    /**得到xpath路径下的所有元素,即一个List对象*/
    public List getElements(String xpath) throws Exception {
        return getElement(root,xpath);
    }
    
    /**得到document的xpath下的所有元素,即一个List对象*/
    public List getElements(Object element,String xpath) throws Exception {
        XPath path = XPath.newInstance(xpath);
        List list = path.selectNodes(element);
        return list;
    }
    
    /**获得以根元素为环境的节点元素*/
    public Element getSpecialElement(String xpath) throws Exception{
        Element element = getSpecialElementByROOT(root,xpath);
        return element;
    }
    
    /**获得根元素下的节点元素,此节点元素是在xpath下的元素,即当前指针下的xpath路径下的元素*/
    public Element getSpecialElementByRelative(String xpath) throws Exception{
        Element element = getSpecialElement(getRelativeXPath(xpath));
        return element;
    }
    
    /**获得指定路径下元素及子元素的所有信息,包括属性及文本值，参数为String xpath*/
    public String getElementAndChildInfo(String xpath) throws Exception {
        Element element = getSpecialElement(xpath);
        return formatElementInfo2XML(element);
    }
    
    /**获得指定路径下元素及子元素的所有信息,包括属性及文本值，参数为String xpath*/
    public String getElementAndChildInfoByRelative() throws Exception {
        Element element = getSpecialElement(based_on_xpath.toString());
        return formatElementInfo2XML(element);
    }
    
    /**获得指定路径下元素及子元素的所有信息,包括属性及文本值，参数为Element element*/
    public String getElementAndChildInfo(Element element) throws Exception {
        return formatElementInfo2XML(element);
    }
    
    /**格式化指定的元素并转换成xml字符串格式,私有方法,参数为Element parent_element*/
    private String formatElementInfo2XML(Element parent_element) throws Exception {
        Element parent = (Element)parent_element.clone();
        List list = parent.getChildren();
        System.out.println("len=="+parent.getText());
        ListIterator iterator = list.listIterator();
        StringBuffer sb = new StringBuffer();
        StringBuffer parent_sb = new StringBuffer();
        int index = 0;
        while(iterator.hasNext()) {
            Element _element = (Element)iterator.next();
            String tag = element2Tag(_element);
            sb.insert(index,tag);
            int len = getTagLength(tag);
            index = index + len;
        }
        StringBuffer tmp = new StringBuffer();
        String parent_tag = element2Tag(parent_element);
        String parent_header = getHeaderOfTag(parent_tag);
        String parent_text = getTextOfTag(parent_tag);
        tmp.append(parent_header);
        tmp.append(parent_text);
        sb.insert(0,tmp.toString());
        String parent_bottom = getBottomOfTag(parent_tag);
        sb.append(parent_bottom);
        return sb.toString();
    }
    public String formatElementInfo2XML(String xpath) throws Exception {
        //Element parent = (Element)parent_element.clone();
       // List list = parent.getChildren();
    	Element parent_element = getSpecialElement(xpath);
        List list = getElements(xpath);
        ListIterator iterator = list.listIterator();
        StringBuffer sb = new StringBuffer();
        StringBuffer parent_sb = new StringBuffer();
        int index = 0;
        while(iterator.hasNext()) {
            Element _element = (Element)iterator.next();
            String tag = element2Tag(_element);
            sb.insert(index,tag);
            int len = getTagLength(tag);
            index = index + len;
        }
        StringBuffer tmp = new StringBuffer();
        String parent_tag = element2Tag(parent_element);
        String parent_header = getHeaderOfTag(parent_tag);
        String parent_text = getTextOfTag(parent_tag);
        tmp.append(parent_header);
        tmp.append(parent_text);
        sb.insert(0,tmp.toString());
        String parent_bottom = getBottomOfTag(parent_tag);
        sb.append(parent_bottom);
        return sb.toString();
    }

    /**
     * 把指定的元素转换成标签对的格式
     *例如:元素element对象转化后为</element></element>字符串标签对
     *如果此元素上有属性、属性值及元素的text值的话也会专成相应的字符串
     *例如:元素test有属性id并且id值为123、元素的text值为hyh的话,那么
     *转化后的字符串格式为<test id=123>hyh</test>
     */
    public String element2Tag(Element element) throws Exception{
        String element_name = element.getName();
        String attr = getNodeAttributes(element);
        String element_text = element.getText();
        StringBuffer sb = new StringBuffer();
        sb.append("<");
        sb.append(element_name);
        sb.append(attr);
        sb.append(">");
        sb.append(element_text);
        sb.append("<");
        sb.append("/");
        sb.append(element_name);
        sb.append(">");
        return sb.toString();
    }
    
    /**获得指定的标签字符串的头即标签的前半部分*/
    public String getHeaderOfTag(String tag) throws Exception{
        String rtn = tag;
        int start_index = tag.indexOf("<");
        int end_index = tag.indexOf(">");
        if(start_index !=0 || end_index == -1) {
            throw new Exception("格式错误");
        }
        rtn = rtn.substring(start_index,end_index+1);
        return rtn;
    }
    
    /**获得指定的标签字符串的头即标签的text值*/
    public String getTextOfTag(String tag) throws Exception{
        String rtn = tag;
        int start_index = tag.indexOf(">");
        int end_index = tag.indexOf("<",1);
        rtn = rtn.substring(start_index+1,end_index);
        return rtn;
    }
    
    /**获得指定的标签字符串的头即标签的后半部分*/
    public String getBottomOfTag(String tag) throws Exception{
        String rtn = tag;
        int start_index = rtn.indexOf("<",1);
        int end_index = rtn.lastIndexOf(">");
        rtn = rtn.substring(start_index,end_index+1);
        return rtn;
    }
    
    /**获得一个标签对的字符串长度*/
    public int getTagLength(String tag) {
        int len = 0;
        int start = tag.indexOf("<");
        int end = tag.lastIndexOf(">");
        len = (end - start)+1;
        return len;
    }
    
    public int getIntNodeText(String xpath) throws Exception {
        String rtn = getNodeText(xpath);
        int text = Integer.parseInt(rtn);
        return text;
    }
    
    public int getIntRelativeNodeText() throws Exception {
        String rtn = getRelativeNodeText();
        int text = Integer.parseInt(rtn);
        return text;
    }
    
    public boolean getBolleanNodeText(String xpath) throws Exception {
        int rtn = getIntNodeText(xpath);
        enumBooleanType enumer = enumBooleanType.FALSE;
        boolean flag = false;
        if(enumer.getEnum(rtn).equals(enumBooleanType.TRUE)) {
            flag = true;
        }
        return flag;
    }
    
    public boolean getBolleanRelativeNodeText() throws Exception {
        return getBolleanRelativeNodeText();
    }
    
    public byte[] getByteArrNodeText(String xpath) throws Exception {
        String rtn = getNodeText(xpath);
        byte[] text = toByteArray(rtn);
        return text;
    }
    
    public byte[] getByteArrRelativeNodeText() throws Exception {
        String rtn = getRelativeNodeText();
        byte[] text = toByteArray(rtn);
        return text;
    }
    
    public float getFloatNodeText(String xpath) throws Exception {
        String rtn = getNodeText(xpath);
        float text = Float.valueOf(rtn);
        return text;
    }
    
    public float getFloatRelativeNodeText() throws Exception {
        String rtn = getNodeText(based_on_xpath.toString());
        float text = Float.valueOf(rtn);
        return text;
    }
    
    public double getDoubleRelativeNodeText(String xpath) throws Exception {
        String rtn = getNodeText(xpath);
        double text = Double.valueOf(rtn);
        return text;
    }
    
    public double getDoubleRelativeNodeText() throws Exception {
        String rtn = getNodeText(based_on_xpath.toString());
        double text = Double.valueOf(rtn);
        return text;
    }
    
    public java.sql.Date getDateNodeText(String xpath) throws Exception {
        String rtn = getNodeText(xpath);
        java.sql.Date date = null;
        try {
            date = java.sql.Date.valueOf(rtn);
        }catch(Exception e) {
            
        }
        return date;
    }
    
    public java.sql.Date getDateRelativeNodeText() throws Exception {
        return getDateNodeText(based_on_xpath.toString());
    }
    
    public java.sql.Timestamp getTimestampNodeText(String xpath) throws Exception {
        String rtn = getNodeText(xpath);
        java.sql.Timestamp timestamp = null;
        try {
            timestamp = java.sql.Timestamp.valueOf(rtn);
        }catch(Exception e) {
            
        }
        return timestamp;
    }
    
    public java.sql.Timestamp getTimestampRelativeNodeText() throws Exception {
        return getTimestampNodeText(based_on_xpath.toString());
    }
    
    public String getNodeText(String xpath) throws Exception {
        return getNodeText(root,xpath);
    }
    
    private String getNodeText(Element _root,String xpath) throws Exception {
        String rtn = "";
        boolean b_flag = xpath.endsWith("/text()");
        if(b_flag) {
            Text text = (Text)XPath.selectSingleNode(_root,xpath);
            if(text !=null) {
                rtn = text.getTextTrim();
            }else {
                return null;
            }
        }else {
            Element elment = (Element)XPath.selectSingleNode(_root,xpath);
            rtn = elment.getTextTrim();
        }
        return rtn;
    }
    
    private String getRelativeNodeText() throws Exception {
        return getNodeText(root,based_on_xpath.toString());
    }
    
    public String getAttributeByRelativeXPath(String attr_name) throws Exception {
        return getAttributeByXPath(root,based_on_xpath.toString(),attr_name);
    }
    
    public String getAttributeByXPath(String xpath,String attr_name) throws Exception {
        return getAttributeByXPath(root,xpath,attr_name);
    }
    
    private String getAttributeByXPath(Element _root,String xpath,String attr_name) throws Exception {
        String rtn = "";
        /*Element element = (Element)XPath.selectSingleNode(_root,xpath);
        rtn = element.getAttributeValue(attr_name);*/
        Attribute attr = getAttrByXPath(_root,xpath,attr_name);
        rtn = attr.getValue();
        return rtn;
    }

	private Element getNodeByXPath(Element _root,String xpath) throws Exception {
        Element element = (Element)XPath.selectSingleNode(_root,xpath);
        return element;
    }

	private Attribute getAttrByXPath(Element _root,String xpath,String attr_anme) throws Exception {
    	Attribute attr = null;
        Element element = getNodeByXPath(_root,xpath);
        if(element!=null){
        	attr = element.getAttribute(attr_anme);
        }else{
        	return null;
        }
        return attr;
    }

	public boolean hasElement(String xpath) throws Exception {
    	boolean rtn = false;
    	if(getNodeByXPath(root,xpath)!=null){
    		rtn = true;
    	}else{
    		rtn = false;
    	}
    	return rtn;
    }
    
    public boolean hasAttribute(String xpath,String attr_name ) throws Exception {
    	boolean rtn = false;
    	if(getAttrByXPath(root,xpath,attr_name)!=null){
    		
    		rtn = true;
    	}else{
    		rtn = false;
    	}
    	return rtn;
    }


    /**设置指针向上一层移动
     *首先截取出以最后一个/字符为结尾的字符串
     *即得到当前路径的上一层路径
     */
    public void setLevelUp() throws Exception{
        int index=based_on_xpath.lastIndexOf("/");
        based_on_xpath.delete(index,based_on_xpath.length());
    }
    
    /**设置指针想上移动index层,如超出索引将抛出异常
     *迭带处理指针向上移
     */
    public void setLevelUp(int index) throws Exception {
        String rtn = based_on_xpath.toString();
        StringBuffer exception = new StringBuffer("无效的引用,超出索引范围,最大索引为");
        String[] count = based_on_xpath.toString().split("/");
        if(index>count.length-1) {
            exception.append(count.length-2);
            throw new Exception(exception.toString());
        }
        for(int i=0;i<index;i++) {
            rtn = rtn.substring(0,rtn.lastIndexOf("/"));
        }
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(rtn);
    }
    
    /**设置指针向下一层移动
     *首先获得当前路径下元素的父节点及父节点下的所有子节点的一个list集合
     *迭代出第一个子节点的路径即是向下移一层的指针位置
     */
    public boolean setLevelDown() throws Exception {
        boolean flag = true;
        String rtn = "";
        StringBuffer tmp_xpath = new StringBuffer();
        Element element = this.getSpecialElementByRelative();
        List list = element.getChildren();
        ListIterator iterator = list.listIterator();
        while(flag =iterator.hasNext()) {                                           
            Element child_element = (Element)iterator.next();
            rtn = child_element.getName();
            break;
        }
        if(!flag) {
            return flag;
        } 
        tmp_xpath.append(based_on_xpath.toString());
        tmp_xpath.append("/");
        tmp_xpath.append(rtn);
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(tmp_xpath);
        return flag;
    }
    
    /**设置指针向下移动index层,循环迭代*/
    public void setLevelDown(int index) throws Exception {
        for(int i=0;i<index;i++) {
            setLevelDown();
        }
        /*String rtn = "";
        StringBuffer tmp_xpath = new StringBuffer();
        StringBuffer exception = new StringBuffer("无效的引用,超出索引范围,最大索引为");
        Element element = this.getSpecialElementByRelative();
        List list = element.getChildren();
        int length = list.size();
        if(index>length) {
            exception.append(length);
            throw new Exception(exception.toString());
        }
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element child_element = (Element)iterator.next();
            if(iterator.nextIndex() == index) {
                rtn = child_element.getName();
                break;
            }
        }
        tmp_xpath.append(based_on_xpath.toString());
        tmp_xpath.append("/");
        tmp_xpath.append(rtn);
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(tmp_xpath);*/
    }
    
    /**设置指针向自己的上一个兄弟节点移动*/
    public boolean setLevelPrevious() throws Exception {
        boolean flag = true;
        String rtn = "";
        Element element = this.getSpecialElementByRelative();
        String parent_xpath = based_on_xpath.substring(0,based_on_xpath.lastIndexOf("/"));
        StringBuffer sb = new StringBuffer(parent_xpath);
        Element parent_element = this.getSpecialElement(parent_xpath);
        List list = parent_element.getChildren();
        ListIterator iterator = list.listIterator();
        while(flag = iterator.hasNext()) {
            Element _element = (Element)iterator.next();
            if(element.equals(_element)) {
                if(iterator.nextIndex()!=1) {                                                 //如果元素不是第一个位置则可向上一个兄弟节点移动否则返回null
                    iterator.previous();                                                      //迭代器向上移动一层,即移动至元素_element路径下，此时指针已经移动至_element元素的前一个内存区
                    Element __element = (Element)iterator.previous();                         //指针再向上移一层便可得到当前路径下元素的上一个兄弟节点
                    rtn = __element.getName();                                                //注:Iterator迭代器的指针永远在当前位置的元素的后一个内存块
                    break;                                                                    //即当iterator第一次迭代处理时即iterator.next()后,指针已指向下一个内存块,
                }else {                                                                       //然后再通过hasNext()判断当前引用的内存块是否有数据,有则允许再一次调用.next(),调用后指针又指向下一个内存块.即指针是先移动后取数据
                    return false;                                                                   //那么当要获得一个元素的上一个兄弟节点的话,指针必须向上移动2次
                }
            }
        }
        if(!flag) {
            return flag;
        }
        sb.append("/");
        sb.append(rtn);
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(sb);
        return flag;
    }
    
    /**设置指针向自己的上index个兄弟节点移动*/
    public void setLevelPrevious(int index) throws Exception {
        String rtn = "";
        StringBuffer exception = new StringBuffer("无效的引用,超出索引范围,最大下移位置为");
        StringBuffer exception2 = new StringBuffer("无效的引用,超出索引范围");
        Element element = this.getSpecialElementByRelative();
        String parent_xpath = based_on_xpath.substring(0,based_on_xpath.lastIndexOf("/"));
        StringBuffer sb = new StringBuffer(parent_xpath);
        Element parent_element = this.getSpecialElement(parent_xpath);
        List list = parent_element.getChildren();
        int length = list.size();
        if(index>length) {
            exception.append(length);
            throw new Exception(exception2.toString());
        }
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element _element = (Element)iterator.next();
            if(element.equals(_element)) {
                    int position = iterator.nextIndex();
                    int max_position = length-1;
                    int circle = position-1;
                    if(circle<=0 || index>max_position || index>circle) {
                        exception.append(circle);
                        throw new Exception(exception.toString());
                    }
                    for(int i=0;i<index;i++) {
                        iterator.previous();
                    }
                    Element __element = (Element)iterator.previous();
                    rtn = __element.getName();
                    break;
            }
        }
        sb.append("/");
        sb.append(rtn);
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(sb);
    }
    
    /**设置指针向自己的下一个兄弟节点移动*/
    public boolean setLevelNext() throws Exception {
        boolean flag = true;
        String rtn = "";
        Element element = this.getSpecialElementByRelative();
        String parent_xpath = based_on_xpath.substring(0,based_on_xpath.lastIndexOf("/"));
        StringBuffer sb = new StringBuffer(parent_xpath);
        Element parent_element = this.getSpecialElement(parent_xpath);
        List list = parent_element.getChildren();
        int length = list.size();
        ListIterator iterator = list.listIterator();
        while(flag = iterator.hasNext()) {
            Element _element = (Element)iterator.next();
            if(element.equals(_element)) {
                if(iterator.nextIndex()!=length) {
                    Element __element = (Element)iterator.next();
                    rtn = __element.getName();
                    break;
                }else {
                    return false;
                }
            }
        }
        if(!flag) {
            return flag;
        }
        sb.append("/");
        sb.append(rtn);
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(sb);
        return flag;
    }
    
    /**设置指针向下index个兄弟节点移动*/
    public void setLevelNext(int index) throws Exception {
        String rtn = "";
        int arr_index = 0;
        int num_index = 0;
        StringBuffer exception = new StringBuffer("无效的引用,超出索引范围,最大下移位置为");
        StringBuffer exception2 = new StringBuffer("无效的引用,超出索引范围");
        Element element = this.getSpecialElementByRelative();
        //String rtns = getXPathByElement(element);
        String parent_xpath = based_on_xpath.substring(0,based_on_xpath.lastIndexOf("/"));
        StringBuffer sb = new StringBuffer(parent_xpath);
        StringBuffer mid_sb = new StringBuffer();;
        Element parent_element = this.getSpecialElement(parent_xpath);
        List list = parent_element.getChildren();
        
        int length = list.size();
        if(index>length) {
            exception.append(exception2);
            throw new Exception(exception.toString());
        }
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Element _element = (Element)iterator.next();
            if(element.equals(_element)) {
                int position = iterator.nextIndex();
                int max_circle = length-1;
                int circle = length-position;
                if(circle<=0 || index>max_circle || index>circle) {
                    exception.append(circle);
                    throw new Exception(exception.toString());
                }
                for(int i=0;i<index-1;i++) {
                    iterator.next();
                }
                Element __element = (Element)iterator.next();
                String tmp = __element.getText();
                if(list.contains(__element)) {
                    int count = list.size();
                    Hashtable ht = new Hashtable();
                    for(int z=0;z<count;z++) {
                        String tmp2 = ((Element)list.get(z)).getName();
                        if(tmp2.equals(__element.getName())) {
                            ht.put(z,new Integer(num_index++));
                        }
                    }
                    int str_count = ((Integer)ht.get(iterator.nextIndex()-1)).intValue() +1 ;
                    rtn = __element.getName();
                    mid_sb.append(rtn);
                    mid_sb.append("[");
                    mid_sb.append(str_count);
                    mid_sb.append("]");
                    rtn = mid_sb.toString();
                }else {
                    rtn = __element.getName();
                }
                break;
            }
        }
        sb.append("/");
        sb.append(rtn);
        based_on_xpath.delete(0,based_on_xpath.length());
        based_on_xpath.append(sb);
    }
    
    /**
     *获得指定元素的当前的相对位置
     *例如:当你不了解xml文档结构并在某个路径下添加了一个节点,那么
     *getRelativeXPathByElement将从指定的元素得到当前元素的相对位置路径
     *返回一个XPath对象
     */
    public String getRelativeXPathByElement(Element element) throws Exception{
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        int index = 0;
        while(!flag) {
            Element parent = (Element)element.getParent();
            if(root.equals(parent)) {
                flag = true;
            }
            sb.insert(0,parent.getName());
            sb.insert(0,"/");
            element = parent;
        }
        if(sb.toString().equals("/")) {
            throw new Exception("没有找到对应的路径");
        }
      
        return sb.toString();
    }
    
    
    /**获得根元素下的节点元素,此节点元素是在based_on_xpath下的元素,即当前指针的位置下的元素*/
    public Element getSpecialElementByRelative() throws Exception{
        Element element = getSpecialElement(based_on_xpath.toString());
        return element;
    }
    
    /**获得相对Root元素的路径上的节点元素*/
    private Element getSpecialElementByROOT(Element element,String xpath) throws Exception {
        Element ele = (Element)XPath.selectSingleNode(element,xpath);
        return ele;
    }
    
    /**解析指定的xml对象,此xml格式为本对象默认的格式*/
    public void forXML(String xml) throws Exception {
        createDefaultXMLDocument(xml);
    }
    
    /**解析指定的inputStream流,此xml格式为本对象默认的格式*/
    public void forXML(java.io.InputStream is) throws Exception {
        createDefaultXMLDocument(is);
    }
    
    /**解析指定的xml对象,此xml格式为本对象默认的格式*/
    public Document forCustomXML(String xml) throws Exception {
        return createCustomXMLDocument(xml);
    }
    
    /**解析指定的xml对象,此xml格式为自定义格式,静态方法*/
    public static Document forCustomXMLByFile(String file_xpath) throws Exception {
        return createCustomXMLDocumentByFile(file_xpath);
    }
    
    /**创建一个新的XMLDocument对象,参数为Element类型.静态方法*/
    public static Document createNewXMLDocument(Element _root) {
        Document document = new Document(_root);
        return document;
    }
    
    /**创建一个指定xml字符串的XMLDocument对象,静态方法*/
    public static Document createCustomXMLDocument(String xml) throws Exception {
        Document _document = getDocumentByXML(xml);
        return _document;
    }
    
    /**创建一个指定xml字符串的XMLDocument对象,静态方法*/
    public static Document createCustomXMLDocumentByFile(String file_xpath) throws Exception {
        Document _document = getDocumentByFile(file_xpath);
        return _document;
    }
    
    /**格式化xml字符串成为本对象支持的格式,静态方法*/
    public static String formatXML(String xml) throws Exception{
        return FormatSpecialXML2DefaultXML(xml);
    }
    
    /**格式化具体操作,静态方法,私有方法*/
    private static String FormatSpecialXML2DefaultXML(String xml) throws Exception{
        String rtn = "";
        StringBuffer xml_buffer = new StringBuffer();
        try {
            Element root=new  Element(ROOT); 
            Element node=getRootElementByXML(xml);
            if(node !=null) {
                Element tmp_node = (Element)node.clone();
                root.addContent(tmp_node);
            }else {
                xml_buffer.append("<hyh>");
                xml_buffer.append(xml);
                xml_buffer.append("</hyh>");
                return xml_buffer.toString();
            }
            
            Document doc= createNewXMLDocument(root);
            rtn=toXML(doc);
        }catch(Exception e) {
            System.out.println(e);
        }
        return rtn;
    }
    
    /**获得指定xml字符串的根元素对象,如果没有就返回null,静态方法,私有方法*/
    private static Element getRootElementByXML(String xml) throws Exception{
        Element root= null;
        if(!hasRootElement(xml)) {
            return null;
        }
        try{
            SAXBuilder saxb=new SAXBuilder();
            StringReader reader=new StringReader(xml);
            Document doc=saxb.build(reader);
            root=doc.getRootElement();
        }catch(Exception e) {
            System.out.println(e);
        }
        return root;
    }
    
    /**判断xml字符串的格式,是否有根节点*/
    public static boolean hasRootElement(String xml) {
        String tmp_xml = "";
        int pos = xml.indexOf("<");
        int pos2 = xml.indexOf(">");
        String tmp = xml.substring(pos+1,pos2);
        int pos_end = xml.lastIndexOf("<");
        int pos2_end = xml.lastIndexOf(">");
        String tmp2 = xml.substring(pos_end+2,pos2_end);
        if(!tmp.equals(tmp2)) {
            return false;
        }else {
            return true;
        }
    }
    
    /**获得指定xml字符串的XMLDocument对象,静态方法*/
    public static Document getDocumentByXML(String xml) throws Exception {
        String tmp_xml = "";
        /*if(!hasRootElement(xml)) {
            tmp_xml = FormatSpecialXML2DefaultXML(xml);
        }else {
            tmp_xml = xml;
        }*/
        SAXBuilder builder = new SAXBuilder();
        StringReader reader = new StringReader(xml);
        Document document = builder.build(reader);
        return document;
    }
    
    public String toXML() {
        String str = toXML(document);
        return str;
    }
    
    public static Document getDocumentByXML(java.io.InputStream is) throws Exception {
        java.io.InputStream input = new java.io.BufferedInputStream(is);
        java.io.InputStreamReader isr = new java.io.InputStreamReader(input,"utf-8");
        java.io.BufferedReader br = new java.io.BufferedReader(isr);
        SAXBuilder builder = new SAXBuilder();
        Document _document = builder.build(br);
        return _document;
    }
    
    public static Document getDocumentByFile(String file_xpath) throws Exception {
        Document _document = null;
        java.io.File file = new File(file_xpath);
        java.io.FileReader fr = new java.io.FileReader(file);
        java.io.BufferedReader br = new java.io.BufferedReader(fr);
        SAXBuilder builder = new SAXBuilder();
        _document = builder.build(br);
        return _document;
    }
    
    /**获得指定XMLDocument对象的字符串形式,静态方法,私有方法*/
    public static String toXML(Document document) {
        XMLOutputter outp=new XMLOutputter();
        org.jdom.output.Format format=org.jdom.output.Format.getPrettyFormat();
        format.setEncoding("utf-8");
        outp.setFormat(format);
        String xml=outp.outputString(document);
        return xml;
    }
    
	public void writeXML(String xml,OutputStream os) throws Exception{
    	   // XMLOutputter xo=new XMLOutputter(" ",true,"GB2312");
    	XMLOutputter xo = new XMLOutputter();
    	org.jdom.output.Format format=org.jdom.output.Format.getPrettyFormat();
        format.setEncoding("utf-8");
        xo.setFormat(format);
    	createDefaultXMLDocument(xml);
    	xo.output(document, os);
    	os.close();

    }
    
    public void writeXML(CXML cxml,OutputStream os) throws Exception{
 	   // XMLOutputter xo=new XMLOutputter(" ",true,"GB2312");
	 	XMLOutputter xo = new XMLOutputter();
	 	org.jdom.output.Format format=org.jdom.output.Format.getPrettyFormat();
        format.setEncoding("utf-8");
        xo.setFormat(format);
	 	xo.output(cxml.getDocment(), os);
	 	os.close();
    }


    /**获得本对象的XMLDocument对象*/
    public Document getDocment() {
        return this.document;
    }
    
    /**将byt[]数组转化为字符串*/
    public String toHexString(byte[] value){
        int max=value.length;
        String rtn="";
        StringBuilder strbuf=new StringBuilder();
        for(int index=0;index<max;index++){
            byte tmp_byte=value[index];
            int tmp_int=tmp_byte;
            strbuf.append(tmp_int);
            if(index!=(max-1)){
                strbuf.append(",");
            }
        }
        rtn=strbuf.toString();
        return rtn;
    }
    
    /**将字符串转化为字节数组*/
    public byte[] toByteArray(String value) throws Exception {
        byte[] rtn=null;
        if(value!=null){
            if(!value.equals("")){
                String [] tmp=value.split(",");
                int max=tmp.length;
                rtn=new byte[max];
                for(int index=0;index<max;index++){
                    Byte b_value=null;
                    b_value=Byte.valueOf(tmp[index]);

                    rtn[index]=b_value.byteValue();
                }
            }
        }
        return rtn;
    }
    
    public int getNodeCount(String xpath) throws Exception {
        int count = 0;
        Element element = getSpecialElement(xpath);
        List list = element.getChildren();
        count = list.size();
        return count;
    }
    
    public int getRelativeNodeCount() throws Exception {
        return getNodeCount(based_on_xpath.toString());
    }
    
    /**设置相对路径*/
    public String setRelativeXPath(String... xpath) {
        int count = xpath.length;
        StringBuffer sb = new StringBuffer();
        String tmp = "";
        for(int i=0;i<count;i++) {
            boolean b = false;
            tmp = xpath[i];
            if(i==0) {
                b = xpath[i].startsWith("/");                       
                if(!b) {
                    based_on_xpath.append("/");
                }
            }
           
        }
        based_on_xpath.append(tmp);
        return based_on_xpath.toString();
    }
    
    /**获得指定参数的相对路径*/
    public String getRelativeXPath(String xpath) {
        String tmp_based_on_xpath = based_on_xpath.toString();                  //将当前的相对路径存入一个临时变量当中
        String _based_on_xpath = setRelativeXPath(xpath);                      //设置传入的参数的相对路径，注意设置完后based_on_xpath会改变为当前的路径
        based_on_xpath.delete(0,based_on_xpath.length());                       //删除改变后的based_on_xpath
        based_on_xpath.append(tmp_based_on_xpath);                              //还原为初始的based_ox_xpath;即改变之前的相对路径的值
        return _based_on_xpath;
    }
    
    /**获得当前的相对路径*/
    public String getRelativeXPath() {
        return based_on_xpath.toString();
    }
    
    /*{"bindings": [
      {"ircEvent": "PRIVMSG", "method": "newURI", "regex": "^http://.*"},
      {"ircEvent": "PRIVMSG", "method": "deleteURI", "regex": "^delete.*"},
      {"ircEvent": "PRIVMSG", "method": "randomURI", "regex": "^random.*"}
      ]};*/
 //var test = {"addressbook": {"name": "Mary Lebow","address": {"street": "5 Main Street","city": "San Diego, CA","zip": 91912},"phoneNumbers": ["619 332-3452","664 223-4667"]}};
    public String xml2JSON(String xml) throws Exception{
        String rtn = "";
        StringBuffer sb = new StringBuffer();
        StringBuffer mid_sb = new StringBuffer();
        String test = "<hyh><item><itemno>dsfd</itemno></item></hyh>";
        forXML(xml);
        Element _root = this.getSpecialElementByRelative();
        List list = getElements(root,"//");
        ListIterator iterator = list.listIterator();
        while(iterator.hasNext()) {
            Object obj = iterator.next();
            if(obj instanceof org.jdom.Element) {
                Element ele = (Element)obj;
                /*List attr_list = ele.getAttributes();
                ListIterator attr_iterator = attr_list.listIterator();
                while(attr_iterator.hasNext()) {
                    Attribute attr_name = (Attribute)attr_iterator.next();
                    String attr = attr_name.getName();
                    String value = attr_name.getValue();
                }*/
                String attr = this.getElementAndChildInfo(ele);
                if(ele.getName().equals(ROOT) && iterator.nextIndex() ==1) {
                    mid_sb.append("\"");
                    mid_sb.append(_root.getName());
                    mid_sb.append("\"");
                    mid_sb.append(": { ");
                    sb.append(mid_sb.toString());
                }else {
                    
                }
            }
        }
        return rtn;
    }
    
    public String formatXML2JSON(String str) throws Exception {
        /*String test5 = "<hyh><itemno><itemname><item></item><car></car></itemname></itemno></hyh>";
        String test3 = "<hyh><itemDetail><itemno>rt300</itemno><itemname>hyh</itemname></itemDetail><itemDetail><itemno>rt400</itemno><itemname>charles</itemname></itemDetail></hyh>";
                        "hyh":{"itemDetail":{"itemno":"rt300","itemname":"hyh"},"itemDetail":{"itemno":"rt300","itemname":"hyh"}}
        String test2 = "<hyh><itemDetail><test22></test22><test></test></itemDetail><test></test></hyh>";
        String test4 = "<hyh><itemDetail></itemDetail><test</test></hyh>";*/
        //var test = {"hyh":[{"itemDetail":{"itemno":"rt300","itemname":"hyh"}},{"itemDetail":{"itemno":"rt400","itemname":"charles"}}]}
        String rtn = "";
        String rtn_text = "";
        StringBuffer sb = new StringBuffer();
        StringBuffer tmp_sb = new StringBuffer();
        StringBuffer mid_sb = new StringBuffer();
        StringBuffer pos_sb = new StringBuffer(str);
        this.forXML(str);
        int count = this.getNodeCount("/hyh");
            Element _root = this.getSpecialElementByRelative();
            rtn = _root.getName();
            sb.append("\"");
            sb.append(rtn);
            sb.append("\":{");
            for(int y=0;y<count;y++) {
                this.setLevelDown();
                if(y !=0) {
                    this.setLevelNext(y);
                }
                Element ele = this.getSpecialElementByRelative();
                System.out.println("xpath"+y+"=="+this.getRelativeXPath());
                mid_sb.append(this.getElementByCircle(this.getRelativeXPath()));
                if(y!=count-1) {
                    mid_sb.append(",");
                }
                tmp_sb.append(mid_sb);
                mid_sb.delete(0,tmp_sb.length());
                String tmp_tmp = this.getRelativeXPath();
                if(!this.setLevelNext()) {
                    this.resetRelativeXPath();
                }
            }
            sb.append(tmp_sb);
            sb.append("}");
        return sb.toString();
    }
    
    public Hashtable getElementArrayByLevel(Element element) throws Exception {
        String rtn = "";
        Element parent_element = (Element)element.getParent();
        System.out.println("parnet_element=="+parent_element.getText());
        List list = parent_element.getChildren();
        if(list == null) {
            return null;
        }
        if(list.size() ==0 || list.size() ==1) {
            return null;
        }
        Object[] arr = list.toArray();
        ListIterator iterator = list.listIterator();
        Hashtable ht = new Hashtable();
        Hashtable ht2 = new Hashtable();
        ArrayList al = new ArrayList();
        boolean flag = true;
        int index = -1;
        for(int i=0;i<arr.length;i++) {
            if(i !=0) {
            	System.out.println("arr=="+arr[i]);
            	System.out.println("ht2=="+((Element)arr[i]).getName());
            	System.out.println("ht2=="+((ArrayList)(ht2.get(((Element)arr[i]).getName()))));
                if(((ArrayList)(ht2.get(((Element)arr[i]).getName()))).contains(i)) {
                    continue;
                }
            }
            for(int y=i+1;y<arr.length;y++) {
                Element ele = (Element)arr[y];
                if(((Element)arr[i]).getName().equals(ele.getName())) {
                    al.clear();
                    if(flag) {
                        al.add(new Integer(i));
                    }
                    flag = false;
                    al.add(new Integer(y));
                    System.out.println("getName=="+((Element)arr[i]).getName());
                    ht2.put(((Element)arr[i]).getName(),al);
                    String str_tmp =this.getElementAndChildInfo(element);
                    String json_tmp = tags2JSONObject(str_tmp);
                    ht.put(((Element)arr[i]).getName(),json_tmp);
                    this.removeNode(ele);
                }
            }
        }
        return ht;
    }

    
    public String getElementByCircle(String xpath) throws Exception {
        Element element = null;
        StringBuffer sb = new StringBuffer();
        element = this.getSpecialElement(xpath);
        String name = element.getName();
        String text = element.getText();
        System.out.println("name=="+name);
        System.out.println("text=="+text);
        Hashtable ht = this.getElementArrayByLevel(element);
        if(this.setLevelDown()) {
            sb.append("\"");
            sb.append(name);
            sb.append("\":");
            //sb.append(text);
            //sb.append("\"");
            sb.append("{");
            System.out.println("current path=="+this.getRelativeXPath());
            sb.append(getElementByCircle(this.getRelativeXPath()));
            sb.append("}");
        }else {
            sb.append("\"");
            sb.append(name);
            sb.append("\":\"");
            if(text.equals("")) {
                text = "null";
            }
            sb.append(text);
            sb.append("\"");
        }
        if(ht !=null) {
            String value = (String)ht.get(name);
            if(name.equals(value)) {
                sb.replace(0,sb.length(),value);
            }
        }
        if(this.setLevelNext()) {
        	 System.out.println("current path2=="+this.getRelativeXPath());
            sb.append(",");
            sb.append(getElementByCircle(this.getRelativeXPath()));
        }
        //"itemno":{"itemname":{item":"null","car":"null"}}
        return sb.toString();
    }
    
    private String tags2JSONObject(String str) throws Exception {
        String rtn = "";
        String rtn_text = "";
        StringBuffer sb = new StringBuffer(str);
        StringBuffer mid_sb = new StringBuffer();
        LinkedList ll = new LinkedList();
        
        sb.delete(sb.indexOf("<"),sb.indexOf(">")+1);
        sb.delete(sb.lastIndexOf("<"),sb.lastIndexOf(">")+1);
        String tmp_str = sb.toString();
        int count = this.getTagCount(tmp_str);
        for(int i=0;i<count;i++) {
            rtn = sb.substring(sb.indexOf("<"),sb.indexOf(">")+1);
            rtn_text = sb.substring(sb.indexOf(">")+1,sb.indexOf("</"));
            if(rtn_text.equals("")) {
                rtn_text = "null";
            }
            mid_sb.append("\"");
            mid_sb.append(rtn);
            mid_sb.append("\"");
            mid_sb.append(":");
            mid_sb.append("\"");
            mid_sb.append(rtn_text);
            mid_sb.append("\"");
            if(i != count) {
                mid_sb.append(",");
            }
            sb.delete(sb.indexOf("<"),sb.indexOf("</"));
            sb.delete(0,sb.indexOf(">")+1);
            rtn = sb.toString();
            
        }
        return mid_sb.toString();
    }
    
    public int getTagCount(String str) throws Exception {
        int rtn = 0;
        //String test = "<hyh><itemDetail><itemno>dd</itemno><itemname>hyh</itemno></itemDetail><itemDetail><item id='456'><itemno>ghjj</itemno></item><itemDetail></hyh>";
        String tmp = ROOT;
        boolean flag = true;
        StringBuffer sb = new StringBuffer(str);
        while(flag) {
            int start_pos = str.indexOf("<");
            int end_pos = str.indexOf(">");
            if(!str.startsWith("</")){
                rtn++;
            }
            sb.delete(0,end_pos+1);
            if(sb.length()==0) {
                return rtn;
            }
            int text_end = sb.indexOf("<");
            String tmp_sb = sb.substring(0,text_end);
            sb.delete(0,text_end);
            str = sb.toString();
            if((str.indexOf("<") ==-1 && str.indexOf(">") ==-1)|| str.length()==0) {
                flag = false;
            }
        }
        rtn = rtn/2;
        return rtn;
    }
    
    public int getTextCount(String str) throws Exception {
        int rtn = 0;
        int text_rtn = 0;
        String tmp = ROOT;
        boolean flag = true;
        StringBuffer sb = new StringBuffer(str);
        while(flag) {
            int start_pos = str.indexOf(">");
            int end_pos = str.indexOf("<");
            String tmp_str = str.substring(start_pos+1,end_pos);
            if(tmp_str.length()!=0) {
                text_rtn++;
            }
            sb.delete(0,end_pos);
            str = sb.toString();
            if((str.indexOf("<") ==-1 && str.indexOf(">") ==-1) || str.length()==0) {
                flag = false;
            }
        }
        return text_rtn;
    }
    
    public static String xml2JSON(CXML xml) {
        String rtn = "";
        
        return rtn;
    }
    
    public static String ht2JSON(Hashtable ht) {
        String rtn = "";
        return rtn;
    }
    
    public static String json2XML(String json) {
        String rtn = "";
        
        return rtn;
    }
    
    public static CXML json2CXML(String json) {
        CXML rtn = null;
        
        return rtn;
    }
    
    public static Hashtable json2HT(String json) {
        Hashtable ht = null;
        return ht;
    }
    
//    public static String truncate(String source,int len) {
//        int byteLen = source.getBytes().length;
//        /*for(int i=0;i<len;i++) {
//           if(source.substring(i,i+1).getBytes().length==1) {
//               byteLen = byteLen + 1;
//           }else {
//               byteLen = byteLen + 2;
//           }
//           
//        }*/
//        String tmp = source.substring(0,len);
//        int tmp_len = tmp.getBytes().length;
//        int start=0,stop=0;
//        try {
//        if(tmp_len<=byteLen) {
//            source = source.substring(0,tmp_len);
//        }else {
//            return source;
//        }
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        return source;
//    }
//    
//    private static boolean translate(String path,TrasformObject pp,OutputStream os){
//    	boolean rtn = false;
//    	try{
//	    	transformobj = pp;
//	    	SAXBuilder sax = new SAXBuilder();
//	    	XMLOutputter xop = new XMLOutputter();
//	    	XSLTransformer transformer = new XSLTransformer(path);
//	    	String xml = transformobj.obj2XML();
//	    	StringReader reader=new StringReader(xml);
//	    	Document xmldoc = sax.build(reader);
//	    	System.out.println("xmldoc=="+CXML.toXML(xmldoc));
//	    	org.jdom.output.Format format=org.jdom.output.Format.getPrettyFormat();
//	        format.setEncoding("utf-8");
//	        format.setOmitDeclaration(false);
//	        xop.setFormat(format);
//	        System.out.println("transformer=="+transformer.transform(xmldoc));
//	        xop.output(transformer.transform(xmldoc), os);
//	    	/*String sHtmlCont = "";// 用于存储临时html代码 
//	    	  String sTemp = "";// 临时变量，用于存取文件中每行的文本文字 
//	    	  String sFileContent = ""; 
//	    	FileInputStream fis = new FileInputStream(path); 
//	    	  InputStreamReader isr = new InputStreamReader(fis); 
//	    	  BufferedReader buf = new BufferedReader(isr); 
//	    	  while ((sTemp = buf.readLine()) != null) 
//	    	  { 
//	    	   // 读取文件内容的每一行合成一个String字符串 
//	    	   sFileContent = sFileContent + sTemp; 
//	    	  } 
//	    	// xsl内容字符串转变为字符流 
//	    	  StringReader bosXslSou = new StringReader(sFileContent); 
//	    	  String xml = transformobj.obj2XML();
//	    	  // xml内容字符串转变为字符流 
//	    	  StringReader bosXmlSou = new StringReader(xml); 
//	    	  TransformerFactory tFactory = TransformerFactory.newInstance(); 
//	    	  Transformer transformer = tFactory.newTransformer 
//	    	   (new javax.xml.transform.stream.StreamSource(bosXslSou)); 
//	    	  /*Properties   properties   =   transformer.getOutputProperties(); 
//	    	  properties.setProperty(OutputKeys.OMIT_XML_DECLARATION,   "yes "); 
//	    	  properties.setProperty(OutputKeys.ENCODING,   "utf-8 "); 
//	    	  properties.setProperty(OutputKeys.METHOD, "html ");   
//	    	  properties.setProperty(OutputKeys.VERSION, "4.0 ");
//	    	  transformer.setOutputProperties(properties); */
//	    	   /*ByteArrayOutputStream bosRes = new ByteArrayOutputStream(); 
//	    	   /*Properties   properties   =   transformer.getOutputProperties(); 
//	    	   properties.setProperty(OutputKeys.OMIT_XML_DECLARATION,   "yes "); 
//	    	   properties.setProperty(OutputKeys.ENCODING,   "utf-8 "); 
//	    	                     properties.setProperty(OutputKeys.METHOD, "html ");   
//	    	                   properties.setProperty(OutputKeys.VERSION, "4.0 ");*/   
//
//	    	   //transformer.setOutputProperties(properties); 
//		    	  
//		    	  
//		    	  /*Reader   xmlR   =   new   StringReader(xml); 
//		    	  SAXReader   reader   =   new   SAXReader(); 
//		    	  DocumentSource   source   =   new   DocumentSource(reader.read(xmlR)); 
//		    	  DocumentResult   result   =   new   DocumentResult(); 
//		    	  transformer.transform(source,   result); 
//		    	  System.out.println("text=="+result.getDocument().getText());*/
//	    	  /*StreamSource ss = new javax.xml.transform.stream.StreamSource(bosXmlSou);
//	    	   javax.xml.transform.stream.StreamResult sr = new javax.xml.transform.stream.StreamResult(bosRes);
//	    	   System.out.println("kkkkk");
//	    	   transformer.transform(ss,sr); 
//	    	   sHtmlCont = new String(bosRes.toString().getBytes("iso8859-1"),"utf-8"); 
//	    	   System.out.println("htmlcont=="+sHtmlCont);*/
//	    	rtn = true;
//    	}catch(Exception e){
//    		System.out.println(e);
//    		rtn = false;
//    	}
//    	return rtn;
//    }
//    
//    public static boolean excute(String path,OutputStream os){
//    	boolean rtn = false;
//    	PageHeader ph = new PageHeader();
//    	PageBody pb = new PageBody();
//    	PageBody pb2 = new PageBody();
//    	try{
//        int len =3;
//        for(int i=0;i<len;i++){
//        
//        	 pb.addPageElement(new PageElement("Goods","",0));
//        	 pb.addPageElementByXPath(new PageElement("hwmc","电脑",1),"//"+CXML.ROOT+"/Goods["+(i+1)+"]");
//        	 pb.addPageElementByXPath(new PageElement("hwsl","10",1),"//"+CXML.ROOT+"/Goods["+(i+1)+"]");
//        	 pb.addPageElementByXPath(new PageElement("hwzl","100",1),"//"+CXML.ROOT+"/Goods["+(i+1)+"]");
//        	 pb.addPageElementByXPath(new PageElement("hwtj","165",1),"//"+CXML.ROOT+"/Goods["+(i+1)+"]");
//        	 pb.addPageElementByXPath(new PageElement("dsdk","￥1000",1),"//"+CXML.ROOT+"/Goods["+(i+1)+"]");
//        	 pb.addPageElementByXPath(new PageElement("ddfy","￥500",1),"//"+CXML.ROOT+"/Goods["+(i+1)+"]");
//        }
//        ph.addPageElement(new PageElement("title","XXX票据打印",0));
//    	ph.addPageElement(new PageElement("Company","山西盟佳联合物流有限公司",0));
//    	pb.addPageElement(new PageElement("billNO","1234567890",0));
//    	pb.addPageElement(new PageElement("acceptDate","2009年12月23日",0));
//    	pb.addPageElement(new PageElement("maker","何玉华",0));
//    	pb.addPageElement(new PageElement("accoutID","admin",0));
//    	pb.addPageElement(new PageElement("acceptTEL","15079114672",0));
//    	pb.addPageElement(new PageElement("shdw","中兴软件技术南昌有限公司",0));
//    	pb.addPageElement(new PageElement("fhdw","中兴软件技术南昌有限公司",0));
//    	pb.addPageElement(new PageElement("shdh","88888888",0));
//    	pb.addPageElement(new PageElement("fhdh","88888888",0));
//    	pb.addPageElement(new PageElement("shdz","江西省南昌市高新区艾湖北路688号中兴软件园A2栋",0));
//    	pb.addPageElement(new PageElement("fhdz","江西省南昌市高新区艾湖北路688号中兴软件园A2栋",0));
//    	pb.addPageElement(new PageElement("qsd","江西省南昌市",0));
//    	pb.addPageElement(new PageElement("mdd","江西省南昌市",0));
//        pb.addPageElement(new PageElement("bzje","￥300",0));
//        pb.addPageElement(new PageElement("bf","￥200",0));
//        pb.addPageElement(new PageElement("yfhj","￥600",0));
//        pb.addPageElement(new PageElement("jsfs","现金",0));
//        pb.addPageElement(new PageElement("fyhj","￥1600",0));
//        pb.addPageElement(new PageElement("memo","非常好very good...",0));
//        pb.addPageElement(new PageElement("cwsr","现金已经支付完毕！",0));
//        pb.addPageElement(new PageElement("skr","何玉华",0));
//        pb.addPageElement(new PageElement("sfz","350103197509220715",0));
//        pb.addPageElement(new PageElement("qsrq","2009年12月23日",0));
//        
//        PageFooter pf = new PageFooter();
//        pf.addPageElement(new PageElement("comment1","迪斯科解放按时打开急口令电视机立刻集散地法迪斯科机抗敌素龙卷风可怜见的立脚点水浮莲",0));
//        pf.addPageElement(new PageElement("comment2","迪斯科解放按时打开急口令电视机立刻集散地法迪斯科机抗敌素龙卷风可怜见的立脚点水浮莲",0));
//        pf.addPageElement(new PageElement("comment3","迪斯科解放按时打开急口令电视机立刻集散地法迪斯科机抗敌素龙卷风可怜见的立脚点水浮莲",0));
//        //xml_dom.addNodeText("comment1","迪斯科解放按时打开急口令电视机立刻集散地法迪斯科机抗敌素龙卷风可怜见的立脚点水浮莲");
//        //xml_dom.addNodeText("comment2","抗敌素飞机地方迪斯科飞机开绿灯时间抗敌素飞机老骥伏枥棵发大而而额外熔解热他热退热退热台热台热台热台日特热台他 热热台儿童饿");
//        //xml_dom.addNodeText("comment3","看法后啊打饥荒地方机翻江倒海十分哭回殴打大师傅呼唤法开会风景画但是");
//        PagePrinter pp = new PagePrinter(ph,pb,pf);
//    	translate(path,pp,os);
//    	}catch(Exception e){
//    		System.out.println(e);
//    	}
//    	return rtn;
//    }
    
}

