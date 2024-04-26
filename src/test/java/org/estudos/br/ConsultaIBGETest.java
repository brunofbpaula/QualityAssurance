package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsultaIBGETest {
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";


    @Test
    @DisplayName("Teste para consulta única de um estado")
    public void testConsultarEstado() throws IOException {
        // Arrange
        String uf = "SP"; // Define o estado a ser consultado

        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    @Test
    @DisplayName("Teste para consultar distrito")
    public void testConsultarDistrito() throws IOException {

        // Número do distrito de Águas de Santa Bárbara
        int distrito = 350055005;
        String expectedReponse = "[" +
                "{\"id\":350055005," +
                "\"nome\":\"Águas de Santa Bárbara\"," +
                "\"municipio\":{\"id\":3500550,\"nome\":\"Águas de Santa Bárbara\"," +
                "\"microrregiao\":{\"id\":35022,\"nome\":\"Avaré\"," +
                "\"mesorregiao\":{\"id\":3504,\"nome\":\"Bauru\"," +
                "\"UF\":{\"id\":35,\"sigla\":\"SP\",\"nome\":\"São Paulo\",\"" +
                "regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}}}," +
                "\"regiao-imediata\":{\"id\":350007,\"nome\":\"Avaré\"," +
                "\"regiao-intermediaria\":{\"id\":3502,\"nome\":\"Sorocaba\"," +
                "\"UF\":{\"id\":35,\"sigla\":\"SP\",\"nome\":\"São Paulo\",\"" +
                "regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}}}}}]";

        HttpURLConnection connectionMock = mock(HttpURLConnection.class);
        when(connectionMock.getInputStream()).thenReturn(new ByteArrayInputStream(expectedReponse.getBytes()));
        when(connectionMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        String response = ConsultaIBGE.consultarDistrito(distrito);
        assert !response.isEmpty();
        assertEquals(expectedReponse, response);

    }
}