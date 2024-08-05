var ws = new WebSocket('ws://localhost:8080');
  ws.onopen = function(e){
    console.log("连接服务器成功");
    const data = { "nickname":"gyk","pubkey":"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDxj2Rly3i83lo6xozwk8SfjFtS39W7/dmx+z4lvMG0c72gt8swqGIK9FDxkeXVeEaCbGSRZ1amsfA+5YENwZ+GusMubITxNX1/RRY8orJuzglDhM4lJz9P61q3TYphy+HP4kn3JfHZXbyL6hM1AAbNFczZoz65LwME8b9PhxaFNL3DuklaLKCXWf8OHSPsywkUcGmGjljZ2Ne6PQG+sbyBc2vk/QXK5jcOzK0PJCyww+1Kbm+7AY9N/DAl+gBsHYM35M3q7Zv5QyGSZgjxdlfEdZctJvJ/0WGcVF4Mx+SOIO4WrBD6uDXA7BQEAFjyr2zZAU2f+8dDYPHMCqvD2hFrAgMBAAECggEAIPdgzEOohp4R0lOmiG6iNuQA0p6ZnL5YJ2ISaTQJe4cQKg7rPAi+2J/fLBitNSTk+oewceeYe8ds8iiAJFB4ZPq4CG3m9sRGDEhxcHUDxPt5aC2HtKpWiaGlD78VU6Nvfd90GLak+vU/9hhL5r/KLdL70fflZ4By+QnFAiV9fVAhdCLghEzqBbm+Qp3BZSAslngTgt1MJTXANwJEN4qLFcfI4hMUKbeZvOXQThzI/BDoLzh0Z6qTI1uZDjNbXqzsY7iCq6LwpqPwPCi6jo4hmDIVlwMsVL/zHW8pnyUoiyqUk4fT1w4iXCBjr8IZmF3YkniWgJDQ5R8/YgimJdvegQKBgQD75/DMvSsC0H3+uw7nEOHUryDd64uLdMizEE/AWuvwEP1btzZnVQ0kX0OJMKPlFWoxKYJBL+zk4y+BW31D9xZ0Tcs4574UriQFSRGk4+nU768kNq0vFlhpi3H+DqBkGFyNMCE2dmGuZ4VG5DOpoq3invJVut16KcgEznt4j3mkXwKBgQD1fGhEFCZNGaR5VIsla6pqPrViiKtcNOxEWxYmjBvR7PHANiJ9lDHRhspAJnv0dm44sLQ084ZjfhHv/7afjCVsxyeYjrP8QdS5XTktzhoEQL+knsEcB5NOPMiG98ccKEhHTw9zgf3fcSMHR+842+3SHhb0bWGUXzQLoxx5xfNOdQKBgQC9yp2plCjwwOb84jCzNQwA80WfDhWgJ4gDKZn8wkQbJqHM1Tf/GJyDN+cpWOVS9xSAH9cMiDBCr0yeeLDjYSmgzO/9WtTiveVTuh66DiGiFkftz+ghnwNT2gUztg9v+s7YbVfIsd6cyVAn/k1SVMgQA1Nv9g9iOFKEFJIAXpD2nwKBgQDRwkbZfRopITMP1XS8Od44wHJ+ymcZ98K2udoYf/m5i6waYGdiRIG5FmK1I+0gux19ZUfmQvvfAbdUCn1CoR7NbvLfgXg7oQiihwy3nfdPEQBwKpK5X6O342o5IXlKDwWLoPuD6FxEU8jpjMbVH997feiBXQFXJW40T2CikMlQjQKBgFxMWt2KPiDTNGcO8fx4BEYpBU5aLYOLZ2guJIB4NEQOT7Rel6AmFV0FHoReaCiJVwUsdvLY8r2p6Fonr6FMPZvBsHnrUqggrVNeNaJFT2e4QIr6opifDZqjstSpLBokUpr+ij3zsT5MXE7rZqEZIgxf6/8doWEc3ULB4mlHQYn4"}

    const jdata = JSON.stringfy(data)
    ws.send(jdata);
  }
  ws.onclose = function(e){
    console.log("服务器关闭");
  }
  ws.onerror = function(){
    console.log("连接出错");
  }
  // 接收服务器的消息
  ws.onmessage = function(e){
    let message = "message:"+e.data+"";
    console.log(message);
  }
