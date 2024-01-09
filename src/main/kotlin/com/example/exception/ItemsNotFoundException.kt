package com.example.exception

import java.util.UUID

class ItemsNotFoundException(uuids: List<UUID>) : RuntimeException("Requested ids $uuids not found")
