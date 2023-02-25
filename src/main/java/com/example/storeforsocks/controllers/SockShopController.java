package com.example.storeforsocks.controllers;

import com.example.storeforsocks.impl.SockShopServiceImpl;
import com.example.storeforsocks.model.Color;
import com.example.storeforsocks.model.Size;
import com.example.storeforsocks.model.Socks;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/socks")
    @Tag(name = "Sock Shop", description = "CRUD операции для работы со складом носков.")
    public class SockShopController {

        private final SockShopServiceImpl sockShopService;

        public SockShopController(SockShopServiceImpl sockShopService) {
            this.sockShopService = sockShopService;
        }

        @GetMapping()
        @Operation(summary = "Вывод количества товара.", description = "Вывод количества носков по заданным параметрам.")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200",
                        description = "Выведено количество товара по запросу."),
                @ApiResponse(responseCode = "400",
                        description = "Параметры запроса отсутствуют или имеют некорректный формат."),
                @ApiResponse(responseCode = "500",
                        description = "Произошла ошибка, не зависящая от вызывающей стороны.")
        })
        public ResponseEntity<Object> getSocks(@RequestParam(name = "color") Color color,
                                               @RequestParam(name = "size") Size size,
                                               @RequestParam(name = "minCotton") int cottonMin,
                                               @RequestParam(name = "maxCotton") int cottonMax) {
            int socksCount = sockShopService.getSocks(color, size, cottonMin, cottonMax);
            if(socksCount == 0){
                ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(socksCount);
        }


        @PostMapping("/add")
        @Operation(summary = "Приход товара на склад.", description = "Добавление носков на склад по их параметрам.")
        @ApiResponses (value = {
                @ApiResponse(responseCode = "200",
                        description = "Товар добавлен на склад.",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))
                        }
                ),
                @ApiResponse(responseCode = "400",
                        description = "Параметры запроса отсутствуют или имеют некорректный формат."),
                @ApiResponse(responseCode = "500",
                        description = "Произошла ошибка, не зависящая от вызывающей стороны.")
        })
        public ResponseEntity<Socks> addSocks (@RequestBody Socks socks) {
            sockShopService.addSocks(socks);
            return ResponseEntity.ok().body(socks);
        }

        @PutMapping("/edit")
        @Operation(summary = "Отпуск товара со склада.",
                description = "Получение носков со склада по параметрам, на складе количество уменьшается.")
        @ApiResponses (value = {
                @ApiResponse(responseCode = "200",
                        description = "Товар получен со склада.",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))
                        }
                ),
                @ApiResponse(responseCode = "400",
                        description = "Параметры запроса отсутствуют или имеют некорректный формат."),
                @ApiResponse(responseCode = "500",
                        description = "Произошла ошибка, не зависящая от вызывающей стороны.")
        })
        public ResponseEntity<Socks> editSocks(@RequestBody Socks socks){
            Socks socks1 = sockShopService.editSocksFromStock(socks);
            if(socks1 == null){
                ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(socks1);
        }

        @DeleteMapping("/remove")
        @Operation(summary = "Списание товара со склада.",
                description = "Получение носков со склада по параметрам, на складе количество уменьшается.")
        @ApiResponses (value = {
                @ApiResponse(responseCode = "200",
                        description = "Товар списан со склада.",
                        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Socks.class))
                        }
                ),
                @ApiResponse(responseCode = "400",
                        description = "Параметры запроса отсутствуют или имеют некорректный формат."),
                @ApiResponse(responseCode = "500",
                        description = "Произошла ошибка, не зависящая от вызывающей стороны.")
        })
        public ResponseEntity<Void> deleteSocks(@RequestBody Socks socks){
            if(sockShopService.removeSocks(socks)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }
    }

