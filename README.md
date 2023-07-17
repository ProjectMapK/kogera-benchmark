This project is a benchmark to compare the performance of `jackson-module-kogera` and `jackson-module-kotlin`.  

- [ProjectMapK/jackson\-module\-kogera](https://github.com/ProjectMapK/jackson-module-kogera)
- [FasterXML/jackson\-module\-kotlin](https://github.com/FasterXML/jackson-module-kotlin)

# About benchmark
`./gradlew jmh` to run the benchmark.  
The benchmark results are output to `jmh-reports/reports/jmh/`.

The main benchmark is to serialize and deserialize for classes with 1, 5, and 20 properties.  

The extra benchmark relate to options that affect performance.  
Currently, benchmarks for deserialization of `Collection` (mainly to check the impact of `strictNullChecks`)
and benchmarks for `value class` have been implemented.  
Also, these benchmarks are not run by default,
and benchmarks related to deserialization of `value class` are only run when using `Kogera`.

A simple configuration is provided in arguments.  
By rewriting the `isSingleShot` property, you can benchmark the performance on the first run.  
By rewriting the `isOnlyMain` property, you can exec all benchmarks.  
By rewriting the `mapper` property, you can benchmark using the `ObjectMapper` configuration defined in [org.wrongwrong.Mapper](./src/jmh/kotlin/org/wrongwrong/Mapper.kt).

Available `mapper` types are as follows

- `Kogera`(default): Not customized `KogeraModule`
- `Original`: Not customized `KotlinModule`
- `KogeraStrictNullCheck`: `KogeraModule` with enabled `strictNullChecks`
- `OriginalStrictNullCheck`: `OriginalModule` with enabled `strictNullChecks`

The following is an example of running all four benchmarks described in the `README` in succession.

```shell
./gradlew jmh -Pmapper=Original -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -Pmapper=Original -PisSingleShot=false -PisOnlyMain=false;
./gradlew jmh -PisSingleShot=true -PisOnlyMain=false;
./gradlew jmh -PisSingleShot=false -PisOnlyMain=false
```

If you want to specify more detailed options, edit `build.gradle.kts`.

# Results
For ease of comparison, descriptions of kogera-specific implementations are separated.

Please note that these scores were measured locally on @k163377 (`Ryzen 7 3700X`/`DDR4 3600`)
and may vary widely depending on load conditions at the time of measurement.

The letters `A` and `E` in the benchmark names are values to adjust the sort order of the results
and represent the number of properties (1 = `A`, 5 = `E`, ...).

## Throughput mode
This is a comparison regarding the case of multiple runs.  
The higher the score, the better.

`kogera` is particularly good at deserialization, being just under three times faster in some use cases.  

There is no significant difference in serialization performance.

#### original(2.15.2)
```
Benchmark                                                    Mode  Cnt        Score        Error  Units
o.w.extra.deser.Collections.array                           thrpt    4   632178.940 ±  62245.856  ops/s
o.w.extra.deser.Collections.list                            thrpt    4   667036.369 ±  19188.400  ops/s
o.w.extra.deser.Collections.map                             thrpt    4   451129.135 ± 104692.358  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call           thrpt    4   922550.390 ±  61783.797  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default   thrpt    4   465611.927 ±   5009.255  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call              thrpt    4   589765.566 ±  17162.595  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default      thrpt    4   393881.907 ± 134149.714  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call           thrpt    4   368225.002 ±   5881.920  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default   thrpt    4   188749.023 ±  37037.545  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call              thrpt    4   249229.020 ±  35884.608  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default      thrpt    4   181385.096 ±  16514.633  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call          thrpt    4   108650.605 ±  12030.540  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default  thrpt    4    60044.770 ±  14198.114  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call             thrpt    4    78049.482 ±   9220.824  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default     thrpt    4    57743.133 ±  13174.738  ops/s
o.w.extra.ser.value_class.A_1Props.call                     thrpt    4  1562454.910 ±  81909.464  ops/s
o.w.extra.ser.value_class.E_5Props.call                     thrpt    4   570140.902 ±   3993.337  ops/s
o.w.extra.ser.value_class.T_20Props.call                    thrpt    4   171288.772 ±  52201.276  ops/s
o.w.extra.ser.wrapper.A_1Props.call                         thrpt    4  2330247.216 ± 333453.447  ops/s
o.w.extra.ser.wrapper.E_5Props.call                         thrpt    4  1321179.029 ± 214073.093  ops/s
o.w.extra.ser.wrapper.T_20Props.call                        thrpt    4   505000.678 ± 158933.581  ops/s
o.w.main.deser.A_1Props_Constructor.call                    thrpt    4   905299.043 ± 171740.844  ops/s
o.w.main.deser.A_1Props_Constructor.call_default            thrpt    4   459963.596 ±  11314.047  ops/s
o.w.main.deser.A_1Props_Function.call                       thrpt    4   581911.821 ± 116910.948  ops/s
o.w.main.deser.A_1Props_Function.call_default               thrpt    4   410564.308 ±  92110.673  ops/s
o.w.main.deser.E_5Props_Constructor.call                    thrpt    4   375518.970 ±  45620.235  ops/s
o.w.main.deser.E_5Props_Constructor.call_default            thrpt    4   188502.131 ±  18832.117  ops/s
o.w.main.deser.E_5Props_Function.call                       thrpt    4   251917.163 ±  24952.497  ops/s
o.w.main.deser.E_5Props_Function.call_default               thrpt    4   174505.158 ±  54319.444  ops/s
o.w.main.deser.T_20Props_Constructor.call                   thrpt    4   118543.101 ±    886.087  ops/s
o.w.main.deser.T_20Props_Constructor.call_default           thrpt    4    55088.109 ±   8251.193  ops/s
o.w.main.deser.T_20Props_Function.call                      thrpt    4    77837.276 ±   4960.774  ops/s
o.w.main.deser.T_20Props_Function.call_default              thrpt    4    57402.649 ±  11043.743  ops/s
o.w.main.ser.A_1Props.call                                  thrpt    4  2403581.570 ± 209826.075  ops/s
o.w.main.ser.E_5Props.call                                  thrpt    4  1398334.939 ± 121217.438  ops/s
o.w.main.ser.T_20Props.call                                 thrpt    4   535899.029 ±  15887.811  ops/s
```

#### kogera(2.15.2-beta0)
```
Benchmark                                                        Mode  Cnt        Score        Error  Units
o.w.extra.deser.Collections.array                               thrpt    4   774703.806 ±  53488.618  ops/s
o.w.extra.deser.Collections.list                                thrpt    4   814753.082 ±  41098.025  ops/s
o.w.extra.deser.Collections.map                                 thrpt    4   517638.575 ±  16942.752  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call               thrpt    4  1154828.273 ± 103538.570  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default       thrpt    4   941950.072 ±   8948.612  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call                  thrpt    4  1181301.691 ± 109605.685  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default          thrpt    4   725201.087 ± 208014.109  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call               thrpt    4   569194.358 ±  13323.826  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default       thrpt    4   476293.570 ±  31753.739  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call                  thrpt    4   621597.240 ± 101973.454  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default          thrpt    4   419742.854 ±  35974.094  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call              thrpt    4   211068.172 ±   3132.383  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default      thrpt    4   156935.677 ±  11461.534  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call                 thrpt    4   218212.803 ±  29224.244  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default         thrpt    4   149933.160 ±   1427.664  ops/s
o.w.extra.ser.value_class.A_1Props.call                         thrpt    4  1542070.273 ± 301374.031  ops/s
o.w.extra.ser.value_class.E_5Props.call                         thrpt    4   561093.169 ±  32251.231  ops/s
o.w.extra.ser.value_class.T_20Props.call                        thrpt    4   175946.764 ±   8389.082  ops/s
o.w.extra.ser.wrapper.A_1Props.call                             thrpt    4  2311022.481 ± 322766.377  ops/s
o.w.extra.ser.wrapper.E_5Props.call                             thrpt    4  1335430.083 ± 146400.269  ops/s
o.w.extra.ser.wrapper.T_20Props.call                            thrpt    4   498429.621 ±  28797.484  ops/s
o.w.main.deser.A_1Props_Constructor.call                        thrpt    4  1201311.398 ±   7925.383  ops/s
o.w.main.deser.A_1Props_Constructor.call_default                thrpt    4   914607.422 ± 246811.604  ops/s
o.w.main.deser.A_1Props_Function.call                           thrpt    4  1203006.064 ± 110149.909  ops/s
o.w.main.deser.A_1Props_Function.call_default                   thrpt    4   728015.213 ± 164352.526  ops/s
o.w.main.deser.E_5Props_Constructor.call                        thrpt    4   632225.586 ±  30226.840  ops/s
o.w.main.deser.E_5Props_Constructor.call_default                thrpt    4   492309.001 ±  60435.988  ops/s
o.w.main.deser.E_5Props_Function.call                           thrpt    4   653883.254 ± 109035.529  ops/s
o.w.main.deser.E_5Props_Function.call_default                   thrpt    4   443158.840 ±  23201.598  ops/s
o.w.main.deser.T_20Props_Constructor.call                       thrpt    4   246905.387 ±   5696.714  ops/s
o.w.main.deser.T_20Props_Constructor.call_default               thrpt    4   164619.358 ±   2758.071  ops/s
o.w.main.deser.T_20Props_Function.call                          thrpt    4   246103.121 ±  79933.789  ops/s
o.w.main.deser.T_20Props_Function.call_default                  thrpt    4   137523.338 ±  19620.937  ops/s
o.w.main.ser.A_1Props.call                                      thrpt    4  2578192.935 ± 254315.859  ops/s
o.w.main.ser.E_5Props.call                                      thrpt    4  1387010.749 ±  37147.939  ops/s
o.w.main.ser.T_20Props.call                                     thrpt    4   536545.727 ±  10233.785  ops/s
```

## SingleShot mode
This is a comparison when the process is executed only once.  
The lower the score, the better.

There is not much difference in deserialization, but `kogera` seems to have areas that can be improved in terms of serialization.

#### original(2.15.2)
```
Benchmark                                                   Mode  Cnt   Score    Error  Units
o.w.extra.deser.Collections.array                             ss    5  26.595 ±  1.790  ms/op
o.w.extra.deser.Collections.list                              ss    5  22.242 ±  1.675  ms/op
o.w.extra.deser.Collections.map                               ss    5  23.491 ±  0.951  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call             ss    5  19.435 ±  0.555  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default     ss    5  21.334 ±  2.385  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                ss    5  20.727 ±  1.798  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default        ss    5  21.738 ±  0.667  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call             ss    5  21.112 ±  2.712  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default     ss    5  22.529 ±  1.904  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                ss    5  24.043 ±  3.071  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default        ss    5  25.476 ±  0.840  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call            ss    5  25.340 ±  4.721  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default    ss    5  25.612 ±  2.441  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call               ss    5  28.285 ±  1.196  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default       ss    5  29.692 ±  3.486  ms/op
o.w.extra.ser.value_class.A_1Props.call                       ss    5  51.549 ±  4.758  ms/op
o.w.extra.ser.value_class.E_5Props.call                       ss    5  55.347 ±  3.913  ms/op
o.w.extra.ser.value_class.T_20Props.call                      ss    5  65.571 ±  3.295  ms/op
o.w.extra.ser.wrapper.A_1Props.call                           ss    5  70.065 ±  4.728  ms/op
o.w.extra.ser.wrapper.E_5Props.call                           ss    5  72.482 ±  2.785  ms/op
o.w.extra.ser.wrapper.T_20Props.call                          ss    5  84.053 ±  7.151  ms/op
o.w.main.deser.A_1Props_Constructor.call                      ss    5  20.146 ±  1.850  ms/op
o.w.main.deser.A_1Props_Constructor.call_default              ss    5  21.853 ±  1.981  ms/op
o.w.main.deser.A_1Props_Function.call                         ss    5  21.343 ±  1.973  ms/op
o.w.main.deser.A_1Props_Function.call_default                 ss    5  22.699 ±  1.055  ms/op
o.w.main.deser.E_5Props_Constructor.call                      ss    5  20.381 ±  1.553  ms/op
o.w.main.deser.E_5Props_Constructor.call_default              ss    5  21.977 ±  1.522  ms/op
o.w.main.deser.E_5Props_Function.call                         ss    5  23.500 ±  2.912  ms/op
o.w.main.deser.E_5Props_Function.call_default                 ss    5  24.888 ±  2.389  ms/op
o.w.main.deser.T_20Props_Constructor.call                     ss    5  25.086 ±  0.641  ms/op
o.w.main.deser.T_20Props_Constructor.call_default             ss    5  26.186 ±  0.397  ms/op
o.w.main.deser.T_20Props_Function.call                        ss    5  28.115 ±  2.802  ms/op
o.w.main.deser.T_20Props_Function.call_default                ss    5  31.844 ± 10.636  ms/op
o.w.main.ser.A_1Props.call                                    ss    5  58.359 ±  6.557  ms/op
o.w.main.ser.E_5Props.call                                    ss    5  61.403 ±  6.337  ms/op
o.w.main.ser.T_20Props.call                                   ss    5  71.533 ±  1.620  ms/op
```

#### kogera(2.15.2-beta0)
```
Benchmark                                                       Mode  Cnt   Score   Error  Units
o.w.extra.deser.Collections.array                                 ss    5  23.730 ± 6.933  ms/op
o.w.extra.deser.Collections.list                                  ss    5  24.502 ± 6.946  ms/op
o.w.extra.deser.Collections.map                                   ss    5  24.664 ± 2.161  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call                 ss    5  19.510 ± 0.911  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default         ss    5  20.784 ± 0.839  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                    ss    5  20.071 ± 1.268  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default            ss    5  21.623 ± 0.956  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call                 ss    5  22.612 ± 2.622  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default         ss    5  24.191 ± 1.682  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                    ss    5  22.445 ± 0.445  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default            ss    5  24.420 ± 1.121  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call                ss    5  26.041 ± 1.911  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default        ss    5  26.777 ± 1.215  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call                   ss    5  27.237 ± 1.730  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default           ss    5  28.882 ± 2.652  ms/op
o.w.extra.ser.value_class.A_1Props.call                           ss    5  77.202 ± 2.085  ms/op
o.w.extra.ser.value_class.E_5Props.call                           ss    5  79.852 ± 2.166  ms/op
o.w.extra.ser.value_class.T_20Props.call                          ss    5  88.698 ± 2.354  ms/op
o.w.extra.ser.wrapper.A_1Props.call                               ss    5  74.039 ± 5.832  ms/op
o.w.extra.ser.wrapper.E_5Props.call                               ss    5  75.399 ± 3.453  ms/op
o.w.extra.ser.wrapper.T_20Props.call                              ss    5  84.695 ± 3.618  ms/op
o.w.main.deser.A_1Props_Constructor.call                          ss    5  19.996 ± 1.539  ms/op
o.w.main.deser.A_1Props_Constructor.call_default                  ss    5  21.705 ± 0.942  ms/op
o.w.main.deser.A_1Props_Function.call                             ss    5  20.583 ± 1.012  ms/op
o.w.main.deser.A_1Props_Function.call_default                     ss    5  22.362 ± 0.733  ms/op
o.w.main.deser.E_5Props_Constructor.call                          ss    5  21.895 ± 1.542  ms/op
o.w.main.deser.E_5Props_Constructor.call_default                  ss    5  23.266 ± 0.861  ms/op
o.w.main.deser.E_5Props_Function.call                             ss    5  23.406 ± 0.523  ms/op
o.w.main.deser.E_5Props_Function.call_default                     ss    5  25.291 ± 1.274  ms/op
o.w.main.deser.T_20Props_Constructor.call                         ss    5  27.081 ± 1.342  ms/op
o.w.main.deser.T_20Props_Constructor.call_default                 ss    5  28.525 ± 1.537  ms/op
o.w.main.deser.T_20Props_Function.call                            ss    5  28.038 ± 0.850  ms/op
o.w.main.deser.T_20Props_Function.call_default                    ss    5  29.528 ± 0.653  ms/op
o.w.main.ser.A_1Props.call                                        ss    5  69.803 ± 3.137  ms/op
o.w.main.ser.E_5Props.call                                        ss    5  72.272 ± 3.200  ms/op
o.w.main.ser.T_20Props.call                                       ss    5  80.935 ± 4.979  ms/op
```

## Comparison of normal class and `value class`
This section compares the serialization and deserialization performance using `Kogera 2.15.2-beta0`
for the `value class` and the `data class` (`wrapper`).

### Throughput mode
As a general trend, there is a decrease in performance when using the `value class`.  
This is because more reflection processing is required when processing `value class`.

```
Benchmark                                                        Mode  Cnt        Score         Error  Units
o.w.extra.deser.value_class.A_1Props_Constructor.call           thrpt    4  1013439.378 ±  24297.251  ops/s
o.w.extra.deser.value_class.A_1Props_Constructor.call_default   thrpt    4   881670.353 ± 408044.396  ops/s
o.w.extra.deser.value_class.A_1Props_Function.call              thrpt    4  1018016.319 ±  18952.134  ops/s
o.w.extra.deser.value_class.A_1Props_Function.call_default      thrpt    4   745726.029 ±  28926.906  ops/s
o.w.extra.deser.value_class.E_5Props_Constructor.call           thrpt    4   448769.203 ±  17304.853  ops/s
o.w.extra.deser.value_class.E_5Props_Constructor.call_default   thrpt    4   497035.613 ±  70997.098  ops/s
o.w.extra.deser.value_class.E_5Props_Function.call              thrpt    4   407380.254 ±  94731.335  ops/s
o.w.extra.deser.value_class.E_5Props_Function.call_default      thrpt    4   437543.491 ±  17544.460  ops/s
o.w.extra.deser.value_class.T_20Props_Constructor.call          thrpt    4   143963.355 ±  21931.038  ops/s
o.w.extra.deser.value_class.T_20Props_Constructor.call_default  thrpt    4   161001.900 ±  21003.385  ops/s
o.w.extra.deser.value_class.T_20Props_Function.call             thrpt    4   139137.647 ±  12716.396  ops/s
o.w.extra.deser.value_class.T_20Props_Function.call_default     thrpt    4   157931.536 ±   3811.466  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call               thrpt    4  1154828.273 ± 103538.570  ops/s
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default       thrpt    4   941950.072 ±   8948.612  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call                  thrpt    4  1181301.691 ± 109605.685  ops/s
o.w.extra.deser.wrapper.A_1Props_Function.call_default          thrpt    4   725201.087 ± 208014.109  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call               thrpt    4   569194.358 ±  13323.826  ops/s
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default       thrpt    4   476293.570 ±  31753.739  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call                  thrpt    4   621597.240 ± 101973.454  ops/s
o.w.extra.deser.wrapper.E_5Props_Function.call_default          thrpt    4   419742.854 ±  35974.094  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call              thrpt    4   211068.172 ±   3132.383  ops/s
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default      thrpt    4   156935.677 ±  11461.534  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call                 thrpt    4   218212.803 ±  29224.244  ops/s
o.w.extra.deser.wrapper.T_20Props_Function.call_default         thrpt    4   149933.160 ±   1427.664  ops/s
o.w.extra.ser.value_class.A_1Props.call                         thrpt    4  1542070.273 ± 301374.031  ops/s
o.w.extra.ser.value_class.E_5Props.call                         thrpt    4   561093.169 ±  32251.231  ops/s
o.w.extra.ser.value_class.T_20Props.call                        thrpt    4   175946.764 ±   8389.082  ops/s
o.w.extra.ser.wrapper.A_1Props.call                             thrpt    4  2311022.481 ± 322766.377  ops/s
o.w.extra.ser.wrapper.E_5Props.call                             thrpt    4  1335430.083 ± 146400.269  ops/s
o.w.extra.ser.wrapper.T_20Props.call                            thrpt    4   498429.621 ±  28797.484  ops/s
```

### SingleShot mode
The scores here also show the same trend as in Throughput mode.

```
Benchmark                                                       Mode  Cnt   Score   Error  Units
o.w.extra.deser.value_class.A_1Props_Constructor.call             ss    5  21.536 ± 1.167  ms/op
o.w.extra.deser.value_class.A_1Props_Constructor.call_default     ss    5  21.369 ± 0.869  ms/op
o.w.extra.deser.value_class.A_1Props_Function.call                ss    5  22.138 ± 1.700  ms/op
o.w.extra.deser.value_class.A_1Props_Function.call_default        ss    5  22.048 ± 0.968  ms/op
o.w.extra.deser.value_class.E_5Props_Constructor.call             ss    5  23.586 ± 1.184  ms/op
o.w.extra.deser.value_class.E_5Props_Constructor.call_default     ss    5  24.127 ± 0.943  ms/op
o.w.extra.deser.value_class.E_5Props_Function.call                ss    5  25.334 ± 2.609  ms/op
o.w.extra.deser.value_class.E_5Props_Function.call_default        ss    5  25.058 ± 1.727  ms/op
o.w.extra.deser.value_class.T_20Props_Constructor.call            ss    5  29.147 ± 2.248  ms/op
o.w.extra.deser.value_class.T_20Props_Constructor.call_default    ss    5  28.703 ± 2.084  ms/op
o.w.extra.deser.value_class.T_20Props_Function.call               ss    5  30.369 ± 2.057  ms/op
o.w.extra.deser.value_class.T_20Props_Function.call_default       ss    5  30.127 ± 1.217  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call                 ss    5  19.510 ± 0.911  ms/op
o.w.extra.deser.wrapper.A_1Props_Constructor.call_default         ss    5  20.784 ± 0.839  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call                    ss    5  20.071 ± 1.268  ms/op
o.w.extra.deser.wrapper.A_1Props_Function.call_default            ss    5  21.623 ± 0.956  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call                 ss    5  22.612 ± 2.622  ms/op
o.w.extra.deser.wrapper.E_5Props_Constructor.call_default         ss    5  24.191 ± 1.682  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call                    ss    5  22.445 ± 0.445  ms/op
o.w.extra.deser.wrapper.E_5Props_Function.call_default            ss    5  24.420 ± 1.121  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call                ss    5  26.041 ± 1.911  ms/op
o.w.extra.deser.wrapper.T_20Props_Constructor.call_default        ss    5  26.777 ± 1.215  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call                   ss    5  27.237 ± 1.730  ms/op
o.w.extra.deser.wrapper.T_20Props_Function.call_default           ss    5  28.882 ± 2.652  ms/op
o.w.extra.ser.value_class.A_1Props.call                           ss    5  77.202 ± 2.085  ms/op
o.w.extra.ser.value_class.E_5Props.call                           ss    5  79.852 ± 2.166  ms/op
o.w.extra.ser.value_class.T_20Props.call                          ss    5  88.698 ± 2.354  ms/op
o.w.extra.ser.wrapper.A_1Props.call                               ss    5  74.039 ± 5.832  ms/op
o.w.extra.ser.wrapper.E_5Props.call                               ss    5  75.399 ± 3.453  ms/op
o.w.extra.ser.wrapper.T_20Props.call                              ss    5  84.695 ± 3.618  ms/op
```
