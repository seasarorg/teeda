//-----------------------------------------------------------------------------
// Excel出力ブロック
// 関連するフィールドが変更されている場合に、処理を中断する。
// 変更されている場合は、エラーメッセージが表示される。
// 
// 関連するフィールドおよびボタンで、本関数の呼び出しを記述する必要がある。
// 
//   ex)
//   関連するフィールド : <input type="text" onchange="fdSubmitHook.notify();" /> 
//   ボタン             : <input type="submit" onclick="fdSubmitHook.hook();" />
//-----------------------------------------------------------------------------
var fdSubmitHook = {

    hookMessageId    : "hookMessage",
    hookMessageText  : "条件が変更されています。",
    hookMessageClass : "error",
    isChanged        :  false,

    notify: function(e) {
        fdSubmitHook.isChanged = true;
    },

    hook: function(e) {
        window.event.returnValue = (!fdSubmitHook.isChanged);
        
        var messageElem = document.getElementById(fdSubmitHook.hookMessageId);
        if(messageElem == null || messageElem == "undefined") {
            fdSubmitHook.viewMessage();
        }
    },
    
    viewMessage: function() {
        var ulTag = document.createElement("ul");
        var liTag = document.createElement("li");
        ulTag.appendChild(liTag);
        
        liTag.id        = fdSubmitHook.hookMessageId;
        liTag.innerHTML = fdSubmitHook.hookMessageText;
        liTag.className = fdSubmitHook.hookMessageClass;
        
        // insert message into form tag.
        document.forms[0].insertBefore(ulTag, document.forms[0].firstChild);
    }

}
