# APD545 WS 2   

## Map Best Practice
Methods I learned about when working with maps.  
`.computeIfAbsent()` - if a key does not exist inside my map default to this behavior  
```
this.dataStore
    .computeIfAbsent(v, key -> new ArrayList<>())
    .add(record);
```
