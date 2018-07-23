package cn.cookie.framework.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cn.cookie.framework.entity.BaseEntity;
import cn.cookie.framework.mybatis.Sort;
import com.alibaba.fastjson.JSONObject;

import cn.cookie.framework.mybatis.complexQuery.CustomQueryParam;

public interface BaseService<T extends BaseEntity> {
	
	public List<T> getAll();
	
	public T getById(String id);

	public T getOne(T findParams);

	public int count(T findParams);

    public int countQuery(List<CustomQueryParam> customQueryParams);

    public List<T> query(List<CustomQueryParam> customQueryParams);

    public List<T> query(List<CustomQueryParam> customQueryParams, Integer start, Integer limit, List<Sort> sortList);

	public List<T> find(T findParams, Integer start, Integer limit);
	
	public List<T> findByObj(T findParams);

	public int insert(T t) ;
	
	public void insert(List<T> list) ;

	public void deleteById(String id) ;

	public void deleteById(List<String> list) ;

	public void delete(T t) ;

	public void delete(List<T> list) ;

    public void deleteAll() ;

	public int update(T t) ;
	
	public void update(List<T> list) ;

//    public void export(OutputStream outputStream, String sheetName, JSONArray columns,JSONObject queryFilter) throws IOException, WriteException, InvocationTargetException,
//            IllegalAccessException,
//            NoSuchMethodException;

    public List<T> findForExport(JSONObject jsonParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    public int countForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    
    public List<T> getByObj(T findParams) ;


}
