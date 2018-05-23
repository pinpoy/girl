package com.imooc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.domain.OrderPay;
import com.imooc.domain.Orderxml;
import com.imooc.domain.Result;
import com.imooc.properties.GirlProperties;
import com.imooc.repository.OrderRepository;
import com.imooc.utils.OrderTools;
import com.imooc.utils.ResultUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/order"})
public class HelloController {
    @Autowired
    private GirlProperties girlProperties;
    private Orderxml orderxml;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping({"/say"})
    public String say(@RequestParam(value = "id", required = false, defaultValue = "0") Integer myId) {
        return "id: " + myId;
    }

    @PostMapping({"/onlinepay"})
    public Result<OrderPay> onlinePay(@Valid OrderPay orderPay, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(Integer.valueOf(1), bindingResult.getFieldError().getDefaultMessage());
        }

        String price = orderPay.getPrice();
        String mobile = orderPay.getMobile();
        String sporderid = orderPay.getSporderid();


        String serviceSign = OrderTools.MD5HEX("price=" + price + "&mobile=" + mobile + "sporderid=" + sporderid);

        if (!serviceSign.equals(orderPay.getSign())) {
            return ResultUtil.error(Integer.valueOf(-1), "验签失败");
        }


        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);

        String userid = "10001795";
        String productid = "";
        String num = "1";
        String spordertime = time;
        String key = "26b3e879b2d8bbae0eeca2907f3fc988";
        String back_url = "http://47.105.57.119:8080/index.html";

        String sign = OrderTools.MD5HEX("userid=" + userid + "&productid=" + productid + "&price=" + price + "&num=" + num + "&mobile=" + mobile + "&spordertime=" + spordertime + "&sporderid=" + sporderid + "&key=" + key);

        String str = "userid=" + userid + "&productid=" + productid + "&price=" + price + "&num=" + num + "&mobile=" + mobile + "&spordertime=" + spordertime + "&sporderid=" + sporderid + "&sign=" + sign + "&back_url=" + back_url;

        System.out.println(str);
        try {
            URL sendUrl = new URL("http://180.96.21.204:29086/onlinepay.do? ");
            HttpURLConnection connection = (HttpURLConnection) sendUrl.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setDoOutput(true);

            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            OutputStream out = connection.getOutputStream();
            out.write(str.getBytes());
            out.flush();
            out.close();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                byte[] result = baos.toByteArray();

                String xmlResult = new String(result, "gb2312");
                is.close();
                System.out.println("客户端执行完毕!!");

                remotetorage(xmlResult);

                return ResultUtil.success(this.orderxml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.error(Integer.valueOf(-1), "未知错误");
    }

    private void remotetorage(String xmlResult) {
        this.orderxml = new Orderxml();

        String orderid = OrderTools.getxmlvalue("orderid", xmlResult);
        String productid = OrderTools.getxmlvalue("productid", xmlResult);
        String num = OrderTools.getxmlvalue("num", xmlResult);
        String ordercash = OrderTools.getxmlvalue("ordercash", xmlResult);
        String productname = OrderTools.getxmlvalue("productname", xmlResult);
        String sporderid = OrderTools.getxmlvalue("sporderid", xmlResult);
        String mobile = OrderTools.getxmlvalue("mobile", xmlResult);
        String merchantsubmittime = OrderTools.getxmlvalue("merchantsubmittime", xmlResult);
        String resultno = OrderTools.getxmlvalue("resultno", xmlResult);
        String fundbalance = OrderTools.getxmlvalue("fundbalance", xmlResult);

        this.orderxml.setOrderid(orderid);
        this.orderxml.setProductid(productid);
        this.orderxml.setNum(num);
        this.orderxml.setOrdercash(ordercash);
        this.orderxml.setProductname(productname);
        this.orderxml.setSporderid(sporderid);
        this.orderxml.setMobile(mobile);
        this.orderxml.setResultno(resultno);
        this.orderxml.setFundbalance(fundbalance);
        //成功的数据保存在数据库的Mysql
        orderRepository.save(orderxml);


        try {
            URL sendUrl = new URL("https://orhvf0bg.api.lncld.net/1.1/classes/orderData");
            HttpURLConnection httpURLConnection = (HttpURLConnection) sendUrl.openConnection();

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            httpURLConnection.setRequestProperty("X-LC-Id", "ORHVF0BG0mjUcSdINFSdbpIx-gzGzoHsz");
            httpURLConnection.setRequestProperty("X-LC-Key", "TsKBD4GiBkcePiixFwCcp8V3");

            httpURLConnection.setDoOutput(true);

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);

            OutputStream out = httpURLConnection.getOutputStream();
            out.write(new ObjectMapper().writeValueAsString(this.orderxml).getBytes());
            out.flush();
            out.close();

            System.out.println("responCode===" + httpURLConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
