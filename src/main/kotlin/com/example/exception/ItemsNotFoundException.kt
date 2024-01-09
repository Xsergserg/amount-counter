package com.example.exception

class ItemsNotFoundException(ids: List<Long>) : RuntimeException("Requested ids $ids not found")
