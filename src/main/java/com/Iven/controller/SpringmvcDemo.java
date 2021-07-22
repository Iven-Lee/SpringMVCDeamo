package com.Iven.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Controller：创建处理器对象，对象放在springmvc容器中
 * 能处理请求的都是控制器（处理器）：
 * SpringmvcDemo类能处理请求，叫做后端控制器（back controller）
 * @RestController == @Controller + @ResponseBody
 * @ResponseBody：表示返回json数组
 */
@Controller
public class SpringmvcDemo {

    /**
     * @RequestMapping（URL地址）：
     * 请求映射，作用是把一个请求地址和一个方法绑定在一起。一个请求指定一个方法处理。
     *             属性：1. value是一个String类型，表示请求的uri地址
     *                     value值是唯一的，不能重复
     *             说明：使用@RequestMapping修饰的方法叫做处理器方法或者控制器方法
     *                   可以处理请求，类似servlet中的doGet,doPost
     *
     * URL地址与jsp请求开始页面的请求地址相同
     *
     * 可加@ResponseBody：表示返回json数组
     */

    /**
     * 返回值 ModelAndView 表示本次请求的处理结果
     *   model:数据，请求处理完后，要显示给用户的数据
     *   view：视图，比如jsp等
     * 返回值还有：String、void、Object
     */
    @RequestMapping(path = "/hello")
    //<a href="hello">入门程序</a><br/>
    public String sayHello(){
        System.out.println("Hello SpringMVC!");
        return "show";
    }


    @RequestMapping(path = "/welcome")
    //<form action="welcome" method="get">
    public ModelAndView testRequestMapping(String name,
                                           Float height,
                                           Float weight){
        /**
         * 逐个接收请求参数：
         *   要求：处理器（控制器）方法的形参名和请求中的参数必须一致
         *       （ <form action="welcome">
         *               姓   名 ：<input type="text" name="name"><br>
         *               身 高(m)：<input type="text" name="height"><br>
         *               体重(kg)：<input type="text" name="weight"><br>
         *               <input type="submit" value="提交参数">
         *          </form>）
         *        同名的请求参数赋值给同名的参数
         *   //若是采用<form action="welcome" method="post">
         *   //在提交请求参数的时候，get请求方式中文没有乱码；post请求方式中文有乱码，需要使用过滤器处理乱码问题
         *   //web.xml中注册声明过滤器，解决post请求乱码的问题
         *
         *
         *   //请求参数名和处理器方法的形参名不一样，使用 @RequestParam 注解
         *   例  public ModelAndView doOther(@RequestParam("iname" ) String name,
         *                                 @RequestParam("iage") Integer age){}
         *
         * 框架接收请求参数：
         * 1 使用request对象接收请求参数
         *   String strName = request.getParameter("name");
         *   String strAge = request.getParameter("age");
         * 2 springmvc框架通过DispatcherServlet 调用MyController的doSome方法
         *   调用方法时，按名称对应，把接收的参数赋值给形参 doSome(strName,Integer.valueOf(strAge))
         *   框架会提供类型转换的功能，把String转换为 int ，long ，float ，double等
         *
         *
         */

        System.out.println("测试RequestMapping注解！");
        ModelAndView mv = new ModelAndView();
        float bmi = weight / (height * height);




        //添加数据，框架在请求的最后把数据放入到request作用域
        //相当于request.setAttribute("n",name);
        mv.addObject("n" ,name);
        mv.addObject("h" ,height);
        mv.addObject("w" ,weight);


        String msg = "";
        if( bmi < 18.5 ){
            msg = "过瘦";
            mv.addObject("fun" ,msg);
        } else if( bmi >= 18.5 && bmi < 23.9 ){
            msg = "正常";
            mv.addObject("fun" ,msg);
        } else if( bmi >=23.9 && bmi <= 27){
            msg = "过重";
            mv.addObject("fun" ,msg);
        } else if(bmi > 27 && bmi < 32 ){
            msg = "肥胖";
            mv.addObject("fun" ,msg);
        } else {
            msg="非常肥胖";
            mv.addObject("fun" ,msg);
        }

        System.out.println("msg = " + msg);



        //指定视图，指定视图的完整路径(采用请求转发方式forward)
        //相当于框架对视图执行的forward操作，request.getRequestDispather("/show.jsp").forward(...)
        //当配置了视图解析器后，可以使用逻辑名称（文件名），指定视图
        //框架会使用视图解析器的 前缀 + 逻辑名称 +后缀 组成完整路径("show")
        //forward：表示转发
        //redirect：表示重定向
        mv.setViewName("show");

        return mv;
    }

}
