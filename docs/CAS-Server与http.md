### 允许http
>即使在上面"授权子应用"添加了http的正则对应规则，但子应用也只能登录而已，并不能实现单点登录。

#### 开发时允许http环境
由于CAS采用了cookie，因此需要保证CAS Server运行在https环境下以保证安全。但开发时可去除这一限制。

1. 修改cas.properties
    ```
    # 允许http
    tgc.secure=false
    warn.cookie.secure=false
    ```
    