function appendEventFor(targetObj, eventName, fn){
  var prev = targetObj[eventName];
  if (prev) {
    targetObj[eventName] = function() { prev(); fn(); }
  } else {
    targetObj[eventName] = fn;
  }
}
