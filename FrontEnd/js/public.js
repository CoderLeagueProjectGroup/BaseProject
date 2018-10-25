var constant={
    host:"http://localhost:8080"
};

//字符串左右去空
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}