package com.example.ticket;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;


public class HttpGetUtils {

    public static String doGet(String stringUrl){
        StringBuilder response = new StringBuilder();
        try {
            // 创建 URL 对象
            URL url = new URL(stringUrl);
            // 创建 HttpURLConnection 对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 设置连接超时和读取超时时间
            connection.setConnectTimeout(5000); // 5秒
            connection.setReadTimeout(5000); // 5秒


            // 读取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 打印响应内容
        return String.valueOf(response);
    }

    public static String doGetNoParameters(String requestURL, String proxyHost, Integer proxyPort) {
        // 记录信息
        StringBuffer buffer = new StringBuffer();

        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestURL);
            // 判断是否需要代理模式请求http
            if (proxyHost != null && proxyPort != null) {
                // 如果是本机自己测试, 不需要代理请求,但发到服务器上的时候需要代理请求
                // 对http开启全局代理
                System.setProperty("http.proxyHost", proxyHost);
                System.setProperty("http.proxyPort", proxyPort.toString());
                // 对https开启全局代理
                //System.setProperty("https.proxyHost", proxyHost);
                //System.setProperty("https.proxyPort", proxyPort);

                // 代理访问http请求
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                // 原生访问http请求，未代理请求
                conn = (HttpURLConnection) url.openConnection();
            }

            // 设置请求的属性
            conn.setDoOutput(true); // 是否可以输出
            conn.setRequestMethod("GET"); // 请求方式, 只包含"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"六种
            conn.setConnectTimeout(5000); // 最高超时时间
            conn.setReadTimeout(5000); // 最高读取时间
            conn.setConnectTimeout(5000); // 最高连接时间

            // 读取数据
            InputStream is = null;
            InputStreamReader inputReader = null;
            BufferedReader reader = null;
            try {
                is = conn.getInputStream();
                inputReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputReader);

                String temp;
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }
            } catch (Exception e) {
                System.out.println("HttpGetUtils doGetNoParameters error: " + e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (inputReader != null) {
                        inputReader.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    System.out.println("HttpGetUtils doGetNoParameters error: " + e);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 当http连接空闲时, 释放资源
            if (conn != null) {
                conn.disconnect();
            }
        }
        // 返回信息
        return buffer.length() == 0 ? "" : buffer.toString();
    }


    /**
     * http请求工具类
     *
     * @param requestURL
     * @param
     * @return
     * @author ouyangjun
     * <p>
     * <p>
     * /**
     * http get请求, 不带参数
     */
    public static String doGetNoParametersSetHeaderClientId(String requestURL, String proxyHost, Integer proxyPort) {
        // 记录信息
        StringBuffer buffer = new StringBuffer();

        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestURL);
            // 判断是否需要代理模式请求http
            if (proxyHost != null && proxyPort != null) {
                // 如果是本机自己测试, 不需要代理请求,但发到服务器上的时候需要代理请求
                // 对http开启全局代理
                System.setProperty("http.proxyHost", proxyHost);
                System.setProperty("http.proxyPort", proxyPort.toString());
                // 对https开启全局代理
                //System.setProperty("https.proxyHost", proxyHost);
                //System.setProperty("https.proxyPort", proxyPort);

                // 代理访问http请求
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                // 原生访问http请求，未代理请求
                conn = (HttpURLConnection) url.openConnection();
            }

            // 设置请求的属性
            conn.setDoOutput(true); // 是否可以输出
            conn.setRequestMethod("GET"); // 请求方式, 只包含"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"六种
            conn.setConnectTimeout(5000); // 最高超时时间
            conn.setReadTimeout(5000); // 最高读取时间
            conn.setConnectTimeout(5000); // 最高连接时间
            conn.setRequestProperty("Client-Id", "wechat");
            // 读取数据
            InputStream is = null;
            InputStreamReader inputReader = null;
            BufferedReader reader = null;
            try {
                is = conn.getInputStream();
                inputReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputReader);

                String temp;
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }
            } catch (Exception e) {
                System.out.println("HttpGetUtils doGetNoParameters error: " + e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (inputReader != null) {
                        inputReader.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    System.out.println("HttpGetUtils doGetNoParameters error: " + e);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 当http连接空闲时, 释放资源
            if (conn != null) {
                conn.disconnect();
            }
        }
        // 返回信息
        return buffer.length() == 0 ? "" : buffer.toString();
    }


    public static String doPost(String stringurl,String jsonInputString){
        StringBuilder response = new StringBuilder();
        try {
            // 设置接口地址
            URL url = new URL(stringurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // 启用输出，因为我们需要发送数据到服务器
            con.setDoOutput(true);

            // 将 JSON 数据作为请求体发送
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 获取服务器的响应
            int responseCode = con.getResponseCode();


            // 读取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(response);
    }

    /**
     * http post请求, 带参数
     *
     * @param requestURL
     * @param params
     * @return
     */
    public static String doPost(String requestURL, String params, String proxyHost, Integer proxyPort) {
        // 记录信息
        StringBuffer buffer = new StringBuffer();
//            HttpURLConnection urlConnection = null;

        HttpURLConnection conn = null;
        try {
            URL url = new URL(requestURL);

//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestProperty("X-Mobile-Version", "");
//                urlConnection.setRequestProperty("X-Menu-Hash", "menuTop");
//                urlConnection.setRequestProperty("X-MenuBottom-Hash", "menuBottom");


            // 判断是否需要代理模式请求http
            if (proxyHost != null && proxyPort != null) {
                // 如果是本机自己测试, 不需要代理请求,但发到服务器上的时候需要代理请求
//                     对http开启全局代理
                System.setProperty("http.proxyHost", proxyHost);
                System.setProperty("http.proxyPort", proxyPort.toString());
                // 对https开启全局代理
                //System.setProperty("https.proxyHost", proxyHost);
                //System.setProperty("https.proxyPort", proxyPort);

                // 代理访问http请求
                Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                // 原生访问http请求，未代理请求
                conn = (HttpURLConnection) url.openConnection();
            }

            // 设置请求的属性
            conn.setDoOutput(true); // 是否可以输出
            conn.setRequestMethod("POST"); // 请求方式, 只包含"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"六种
            conn.setConnectTimeout(60000); // 最高超时时间
            conn.setReadTimeout(60000); // 最高读取时间
            conn.setConnectTimeout(60000); // 最高连接时间
            conn.setDoInput(true); // 是否可以输入

            //添加请求头信息
            conn.setRequestProperty("", "");


            if (params != null) {
                // 设置参数为json格式
                conn.setRequestProperty("Content-type", "application/json");

                // 写入参数信息
                OutputStream os = conn.getOutputStream();
                try {
                    os.write(params.getBytes("UTF-8"));
                } catch (Exception e) {
                    System.out.println("HttpPostUtils doPost error: " + e);
                } finally {
                    try {
                        if (os != null) {
                            os.close();
                        }
                    } catch (IOException e) {
                        System.out.println("HttpPostUtils doPost error: " + e);
                    }
                }
            }

            // 读取数据
            InputStream is = null;
            InputStreamReader inputReader = null;
            BufferedReader reader = null;
            try {
                is = conn.getInputStream();
                inputReader = new InputStreamReader(is, "UTF-8");
                reader = new BufferedReader(inputReader);

                String temp;
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }
            } catch (Exception e) {
                System.out.println("HttpPostUtils doPost error: " + e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (inputReader != null) {
                        inputReader.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    System.out.println("HttpPostUtils doPost error: " + e);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 当http连接空闲时, 释放资源
            if (conn != null) {
                conn.disconnect();
            }
        }
        // 返回信息
        return buffer.length() == 0 ? "" : buffer.toString();
    }


}