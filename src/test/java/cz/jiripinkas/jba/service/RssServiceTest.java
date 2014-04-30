package cz.jiripinkas.jba.service;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.exception.RssException;

public class RssServiceTest {

	private RssService rssService;

	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}

	@Test
	public void testGetItemsFileJavaVids() throws RssException {
		List<Item> items = rssService.getItems(new File("test-rss/javavids.xml"));
		assertEquals(10, items.size());
		Item firstItem = items.get(0);
		assertEquals("How to generate web.xml in Eclipse", firstItem.getTitle());
		assertEquals("23 03 2014 09:01:34", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
	}

	@Test
	public void testGetItemsFileSpring() throws RssException {
		List<Item> items = rssService.getItems(new File("test-rss/spring.xml"));
		assertEquals(20, items.size());
		Item firstItem = items.get(0);
		assertEquals("Spring Boot 1.0.1.RELEASE Available Now", firstItem.getTitle());
		assertEquals("https://spring.io/blog/2014/04/07/spring-boot-1-0-1-release-available-now", firstItem.getLink());
		assertEquals("07 04 2014 10:14:00", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
		if (!firstItem.getDescription().startsWith("Spring Boot 1.0.1.RELEASE is available")) {
			fail("description does not match");
		}
	}

	@Test
	public void testGetItemsFileHibernate() throws RssException {
		List<Item> items = rssService.getItems(new File("test-rss/hibernate.xml"));
		assertEquals(14, items.size());
		Item firstItem = items.get(0);
		assertEquals("Third milestone on the path for Hibernate Search 5", firstItem.getTitle());
		assertEquals("http://in.relation.to/Bloggers/ThirdMilestoneOnThePathForHibernateSearch5", firstItem.getLink());
		assertEquals("04 04 2014 17:20:32", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
		if (!firstItem.getDescription().startsWith("Version 5.0.0.Alpha3 is now available")) {
			fail("description does not match");
		}
	}

	@Test
	public void testPullLinks() {
		String inputText = "Hibernate ORM 4.2.12.Final was just released! Please see the full changelog for more information: https://hibernate.atlassian.net/secure/ReleaseNote.jspa?projectId=10031&version=16350. "
				+ "Maven Central: http://repo1.maven.org/maven2/org/hibernate/hibernate-core (should update in a couple of days) "
				+ "SourceForge: www.sourceforge.net/projects/hibernate/files/hibernate4 "
				+ "JBoss Nexus: ftp://repository.jboss.org/nexus/content/groups/public/org/hibernate "
				+ " mailto:test@email.com";
		ArrayList<String> links = rssService.pullLinks(inputText);
		assertEquals(5, links.size());
		assertEquals(links.get(0), "https://hibernate.atlassian.net/secure/ReleaseNote.jspa?projectId=10031&version=16350");
		assertEquals(links.get(1), "http://repo1.maven.org/maven2/org/hibernate/hibernate-core");
		assertEquals(links.get(2), "www.sourceforge.net/projects/hibernate/files/hibernate4");
		assertEquals(links.get(3), "ftp://repository.jboss.org/nexus/content/groups/public/org/hibernate");
		assertEquals(links.get(4), "mailto:test@email.com");
	}
	
	@Test
	public void testCleanTitle() {
		assertEquals("test", rssService.cleanTitle("   test   "));
		assertEquals("Pat & Mat", rssService.cleanTitle("Pat & Mat"));
		assertEquals("link:", rssService.cleanTitle("link: <a href='http://something.com'></a>"));
		assertEquals("script:", rssService.cleanTitle("script: <script><!-- alert('hello'); --></script>"));
	}
	
	@Test
	public void testGetRssDate() throws ParseException {
		assertEquals("Sun Mar 23 09:01:34 CET 2014", rssService.getRssDate("Sun, 23 Mar 2014 08:01:34 +0000").toString());
		assertEquals("Sun Mar 23 00:00:00 CET 2014", rssService.getRssDate("Sun, 23 Mar 2014").toString());
	}

}