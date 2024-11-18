package com.example.tarea;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HolaMundoController {

    private final DynamoDbClient dynamoDbClient;

        @GetMapping("/hola")
    public String holaMundo() {
        return "hola";
    }

    @GetMapping("/clima")
    public String mostrarClima() {
        return "clima";
    }

    @GetMapping("/data/clima")
    @ResponseBody
    public List<Map<String, Object>> obtenerClima() {
        String tableName = "PrecipitacionTemperaturaMundial";

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        return scanResponse.items().stream()
                .map(item -> item.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> convertirValor(entry.getValue())
                        )))
                .collect(Collectors.toList());
    }


    public HolaMundoController() {
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        "aws_access_key_id",      
                                        "aws_secret_access_key"
                                )
                        )
                )
                .build();
    }

    private Object convertirValor(AttributeValue value) {
        if (value.s() != null) {
            return value.s();
        } else if (value.n() != null) {
            return Double.parseDouble(value.n());
        } else if (value.bool() != null) {
            return value.bool();
        } else {
            return null;
        }
    }
}