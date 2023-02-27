package com.ghh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ghh.reggie.entity.Setmeal;
import com.ghh.reggie.mapper.SetmealMapper;
import com.ghh.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
