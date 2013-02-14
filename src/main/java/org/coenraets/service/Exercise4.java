package org.coenraets.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheWriterConfiguration;
import org.coenraets.model.Wine;

/**
 * @author Christophe Coenraets
 */
public class Exercise4 implements WineService {
	private WineMysql mysql;
	private Cache wineCache;

	public Exercise4() {
		mysql = new WineMysql();

//		CacheManager manager = CacheManager.getInstance();
//
//		if ( !manager.cacheExists( "wine4" ) ) {
//			CacheConfiguration cacheConfiguration = new CacheConfiguration( "wine4", 10000 );
//			cacheConfiguration.cacheWriter( new CacheWriterConfiguration().writeMode( CacheWriterConfiguration.WriteMode.WRITE_BEHIND ) );
//			Cache cache = new Cache( cacheConfiguration );
//			manager.addCache( cache );
//		}
//
//		wineCache = manager.getCache( "wine4" );
		wineCache = CacheManager.create( "src/main/resources/ehcache-ex7.xml" ).getCache( "wine4" );

		wineCache.registerCacheWriter( new MyCacheWriter() );
	}

	@Override
	public List<Wine> findAll() {
		throw new RuntimeException( "not implemented" );
	}


	@Override
	public List<Wine> findByName(String name) {
		throw new RuntimeException( "not implemented" );
	}

	@Override
	public Wine findById(long id) {
		Element element1 = wineCache.get( id );
		if ( element1 == null ) {
			Wine wine = mysql.findById( id );
			Element element = new Element( id, wine );
			wineCache.put( element );
			return wine;
		}
		return (Wine) element1.getObjectValue();
	}

	@Override
	public Wine save(Wine wine) {
		throw new RuntimeException( "not implemented" );
	}

	@Override
	public Wine create(Wine wine) {
//		Long id = System.currentTimeMillis();
		Element element = new Element( wine.getId(), wine );
		wineCache.putWithWriter( element );
		return wine;
	}

	@Override
	public Wine update(Wine wine) {
		throw new RuntimeException( "not implemented" );

	}

	@Override
	public boolean remove(long id) {
		throw new RuntimeException( "not implemented" );
	}

	@Override
	public void clear() {
		wineCache.removeAll();
	}

	@Override
	public void init() {
	}

	public void setMysql(final WineMysql mysql) {
		this.mysql = mysql;
	}

	public void setCache(final Cache wineCache) {
		this.wineCache = wineCache;
	}
}
