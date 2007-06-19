SmartDeployのモードによって、env.txtの値を切り替えてください。
現在のteeda-html-exampleでは、下記のようになっています。
・ut : HotDeployで動きます。
・ct : WarmDeployで動きます。
・it : WarmDeployで動きます。
・product : CooｌDeployで動きます。

各環境名（ut,ct,it,product）は自由に設定することができます。
現在のデフォルトの設定はutとなっています。

現在どのSmartDeployのモードで稼動しているかは、下記URLで確認できます。
http://localhost:8080/teeda-html-example/teedaServlet?command=list