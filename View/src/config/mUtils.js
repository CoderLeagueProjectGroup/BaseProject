/**
 * 存储localStorage
 * @param {string} name 
 * @param {*} content 
 */
export const setStore = (name,content)=>{
    if(!name) return;
    if(typeof content !== 'string'){
        content = JSON.stringify(content);
    }
    window.localStorage.setItem(name,content);
}

/**
 * 获取localStorage
 * @param {string} name 
 */
export const getStore = name => {
    if(!name) return;
    return window.localStorage.getItem(name);
}

/**
 * 删除localStorage
 * @param {string} name 
 */
export const removeStore = name => {
    if(!name) return;
    return window.localStorage.removeItem(name);
}
