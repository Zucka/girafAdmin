package dk.aau.cs.giraf.savannah.test.JUnit;

import java.util.ArrayList;

import org.jdom.Document;

import dk.aau.cs.giraf.savannah.io.DOMinator.DOMinator;
import dk.aau.cs.giraf.savannah.server.QueryBuilder;

import junit.framework.TestCase;

public class QueryBuilderJUnit extends TestCase {
	private String xml;
	private DOMinator dom;
	private Document doc;
	private QueryBuilder qbuilder;
	private ArrayList<String> queries;
	public QueryBuilderJUnit(String name)
	{
		super(name);
	}
	
	protected void setUp() throws Exception
	{
		dom = new DOMinator();
		qbuilder = new QueryBuilder();
		queries = new ArrayList<String>();
		super.setUp();
	}
	
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	
	public void testCreateBuildSingleQuery() throws Exception
	{
		String xml = "<sw6ml><AuthUsers><Entry action=\"create\">" +
				"<certificate type=\"string\">123</certificate>" +
				"</Entry>" +
				"</AuthUsers></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("INSERT INTO AuthUsers values('123');", one.get(0) );	
	}
	
	public void testUpdateBuildSingleQuery() throws Exception
	{
		String xml = "<sw6ml><AuthUsers><Entry action=\"update\">" +
				"<certificate type=\"string\">123</certificate>" +
				"<idUser type=\"int\">1</idUser>"+
				"</Entry>" +
				"</AuthUsers></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("UPDATE AuthUsers SET idUser=1 WHERE certificate='123';", one.get(0) );	
	}
	
	public void testDeleteBuildSingleQuery() throws Exception
	{
		String xml = "<sw6ml><AuthUsers><Entry action=\"delete\">" +
				"<certificate type=\"string\">123</certificate>" +
				"</Entry>" +
				"</AuthUsers></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("DELETE FROM AuthUsers WHERE certificate='123';", one.get(0) );	
	}
	
	public void testCreateBuildSingleQueryDifTable() throws Exception
	{
		String xml = "<sw6ml><Media><Entry action=\"create\">" +
				"<mPath type=\"string\">/something</mPath>" +
				"</Entry>" +
				"</Media></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("INSERT INTO Media values('/something');", one.get(0) );	
	}
	
	public void testUpdateBuildSingleQueryDifTable() throws Exception
	{
		String xml = "<sw6ml><Media><Entry action=\"update\">" +
				"<mPath type=\"string\">/something</mPath>" +
				"<idMedia type=\"int\">1</idMedia>"+
				"</Entry>" +
				"</Media></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("UPDATE Media SET idMedia=1 WHERE mPath='/something';", one.get(0) );	
	}
	
	public void testDeleteBuildSingleQueryDifTable() throws Exception
	{
		String xml = "<sw6ml><Media><Entry action=\"delete\">" +
				"<mPath type=\"string\">/something</mPath>" +
				"</Entry>" +
				"</Media></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("DELETE FROM Media WHERE mPath='/something';", one.get(0) );	
	}
	
	public void testCreateIntValue() throws Exception
	{
		String xml = "<sw6ml><Media><Entry action=\"create\">" +
				"<idMedia type=\"int\">1</idMedia>" +
				"</Entry>" +
				"</Media></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("INSERT INTO Media values(1);", one.get(0) );	
	}
	
	public void testUpdateIntValue() throws Exception
	{
		String xml = "<sw6ml><Media><Entry action=\"update\">" +
				"<idMedia type=\"int\">1</idMedia>"+
				"<mPath type=\"string\">/something</mPath>" +
				"</Entry>" +
				"</Media></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("UPDATE Media SET mPath='/something' WHERE idMedia=1;", one.get(0) );	
	}
	
	public void testDeleteIntValue() throws Exception
	{
		String xml = "<sw6ml><Media><Entry action=\"delete\">" +
				"<idMedia type=\"int\">1</idMedia>" +
				"</Entry>" +
				"</Media></sw6ml>";
		doc = dom.Dominate(xml);
		ArrayList<String> one = qbuilder.buildQueries(doc);
		assertEquals("DELETE FROM Media WHERE idMedia=1;", one.get(0) );	
	}
}
