package com.imooc.repository;

import com.imooc.domain.Orderxml;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单回调成功
 * 2016-11-03 23:17
 */
public interface OrderRepository extends JpaRepository<Orderxml, Integer> {


}
