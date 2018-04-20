package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.module.system.dao.SysDictMapper;
import com.springboot.ssmybt.module.system.entity.SysDict;
import com.springboot.ssmybt.module.system.service.SysDictService;
import static com.springboot.ssmybt.common.factory.MutiStrFactory.*;

@Service
public class SysDictServiceImpl extends IServiceImpl<SysDict> implements SysDictService {
	
	@Autowired
	private SysDictMapper dictMapper;
	
	@Override
	public List<Map<String, Object>> selectDictList(@Param("condition") String condition) {
		return dictMapper.selectDictList(condition);
	}

	@Override
	public SysDict selectById(Integer dictId) {
		return dictMapper.selectById(dictId);
	}

	@Override
	public List<SysDict> selectList(@Param("ew")Wrapper<SysDict> wrapper) {
		return dictMapper.selectList(wrapper);
	}

    @Override
    public void addDict(String dictName, String dictValues) {
        //判断有没有该字典
        List<SysDict> dicts = dictMapper.selectList(new EntityWrapper<SysDict>().eq("name", dictName).and().eq("pid", 0));
        if(dicts != null && dicts.size() > 0){
            throw new SystemException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = parseKeyValue(dictValues);

        //添加字典
        SysDict dict = new SysDict();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MUTI_STR_KEY);
            String name = item.get(MUTI_STR_VALUE);
            SysDict itemDict = new SysDict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            }catch (NumberFormatException e){
                throw new SystemException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    public void editDict(Integer dictId, String dictName, String dicts) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictName,dicts);
    }

    @Override
    public void delteDict(Integer dictId) {
        //删除这个字典的子词典
        Wrapper<SysDict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.deleteChirld(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }

}
