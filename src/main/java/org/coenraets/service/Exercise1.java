package org.coenraets.service;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.coenraets.model.Wine;

/**
 * Des indices pour créer le cache sont dans le fichier tips1.txt dans ce meme répertoire !
 * Saurez vous vous en passer ? :)
 */
public class Exercise1 implements WineService {
	private WineMysql mysql;
	private Cache wineCache;

	public Exercise1() {
		mysql = new WineMysql();
		wineCache = CacheManager.create( "src/main/resources/ehcache-ex7.xml" ).getCache( "wine1" );

//		Configuration config = new Configuration();
//		CacheConfiguration myCache = new CacheConfiguration("wine1", 10000);
//		config.addCache(myCache);
//		CacheManager mgr =  CacheManager.create(config);
//		wineCache = mgr.getCache( "wine1" );

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
		if ( element1 == null) {
			Wine wine = mysql.findById( id );
			Element element = new Element(id, wine);
			wineCache.put( element);
			return wine;
		}
		return (Wine)element1.getObjectValue();


	}

	@Override
	public Wine save(Wine wine) {
		throw new RuntimeException( "not implemented" );
	}

	@Override
	public Wine create(Wine wine) {
		throw new RuntimeException( "not implemented" );
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
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public Cache getCache() {
		return wineCache;
	}

	public void setMysql(final WineMysql mysql) {
		this.mysql = mysql;
	}

	public void setWineCache(final Cache wineCache) {
		this.wineCache = wineCache;
	}
}
