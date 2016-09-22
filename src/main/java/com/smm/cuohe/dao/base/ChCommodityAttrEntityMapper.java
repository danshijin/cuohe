package com.smm.cuohe.dao.base;

import java.util.List;
import java.util.Map;

import com.smm.cuohe.domain.base.ChCommodityAttrEntity;

public interface ChCommodityAttrEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChCommodityAttrEntity record);

    int insertSelective(ChCommodityAttrEntity record);

    ChCommodityAttrEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChCommodityAttrEntity record);

    int updateByPrimaryKey(ChCommodityAttrEntity record);

	List<ChCommodityAttrEntity> queryCommodityAttrList(Map<String, Object> params);
}