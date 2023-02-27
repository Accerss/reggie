package com.ghh.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghh.reggie.common.CustomExeception;
import com.ghh.reggie.entity.Category;
import com.ghh.reggie.entity.Dish;
import com.ghh.reggie.entity.Setmeal;
import com.ghh.reggie.mapper.CategoryMapper;
import com.ghh.reggie.service.CategoryService;
import com.ghh.reggie.service.DishService;
import com.ghh.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除类，删除之前校验是否可删除
     * @param id
     */
    @Override
    public void remove(Long id){
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            //存在关联的菜品，抛出异常
            throw new CustomExeception("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //存在关联的套餐，抛出异常
            throw new CustomExeception("当前分类下关联了套餐，不能删除");
        }

        //删除分类
        super.removeById(id);
    }
}
