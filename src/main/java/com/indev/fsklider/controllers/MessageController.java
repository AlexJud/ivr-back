package com.indev.fsklider.controllers;

//@RestController
//@RequestMapping
public class MessageController {

//    private LinkedList<ResponseEvent> events = new LinkedList<>();
//    private LinkedList<ResponseEvent> eventsHistory = new LinkedList<>();
//
//    private Map<String, String> result;
//
//    @GetMapping("/message")
//    public ResponseEntity<Map<String, String>> getMessage() {
//        return ResponseEntity.ok(result);
//    }
//
//    @PostMapping("/message")
//    public void setMessage(@RequestBody Map<String, String> message) {
//        result = message;
//    }
//
//    @GetMapping(value = "/api/event", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<List<ResponseEvent>> getEvent() {
//        if (events != null && events.size() > 0) {
//            eventsHistory.addAll(events);
//            LinkedList<ResponseEvent> res = new LinkedList<>(events);
//            events.clear();
//            return ResponseEntity.ok(res);
//        } else
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//    @GetMapping(value = "/api/reset", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public void Reset() {
//        if (eventsHistory != null && eventsHistory.size() > 0) {
//            events.addAll(eventsHistory);
//            eventsHistory.clear();
//        }
//    }
//
//    @PostMapping("/event")
//    public void setEvent(@RequestBody ResponseEvent event) {
//        events.addLast(event);
//    }

}
