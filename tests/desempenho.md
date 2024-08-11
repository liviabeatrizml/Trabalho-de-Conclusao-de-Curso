# Testes de Desempenho

## 💡 Introdução
Os testes de desempenho visam identificar falhas na garantia da qualidade de um produto, procurando cobrir aspectos de desempenho que expõem o sistema a cenários específicos. 

Dessa forma, foram escolhidas as ferramentas JMeter onde foi empregado os testes de carga e estresse, baseando-se nos números de usuários ativos da instituição. Análogo, a ferramenta Google Lighthouse foi aplicada para verificar aspectos de qualidade em páginas autenticadas.

## ⚙️ Ferramentas Utilizadas
### Google Lighthouse
As métricas disponibilizadas pela ferramenta permite a análise dos seguintes aspectos:

- **Performance**: tempo de carregamento, resposta de recursos, índice de velocidade.
- **Acessibilidade**: elementos que impactam acessibilidade para diversos usuários.
- **Melhores Práticas**: erros de compatibilidade, otimização de imagens, segurança.
- **SEO - Search Engine Optimization**: capacidade de indexação, uso de meta tags, adaptabilidade para dispositivos móveis.
- **PWA - Progressive Web App**: examina se o código responde corretamente, assim como o tempo de carregamento rápido em redes 3G.

⚠️ Os testes não incluíram medições de PWA (Progressive Web App), pois essa funcionalidade não está implementada no sistema.

### JMeter
Os cenários de carga foram definidos com base nos números de usuários ativos da instituição em abril de 2024: 1322 discentes, 79 docentes e 39 técnicos administrativos, resultando nos seguintes cenários:
- **Carga Mínima**: 360 usuários
- **Carga Média**: 720 usuários
- **Carga Máxima**: 1440 usuários

## 📊 Resultados dos Testes - Lighthouse

⚠️ **D** para métricas em relação a Desktop.
⚠️ **M** para métricas em relação a Mobile.

| Páginas | Desempenho (D)| Acessibilidade (D) | Boas Práticas (D) | SEO (D) | Desempenho (M) | Acessibilidade (M) | Boas Práticas (M) | SEO (M) |
|--------------------|------------|----------------|---------------|-----|------------|----------------|---------------|-----|
| **Home**           | 82         | 96             | 74            | 91  | 58         | 96             | 75            | 91  |
| **Nova Reserva**   | 83         | 93             | 74            | 82  | 57         | 88             | 75            | 82  |
| **Reserva em Lote**| 82         | 93             | 74            | 82  | 57         | 88             | 74            | 82  |
| **Minhas Reservas**| 83         | 90             | 74            | 82  | 56         | 85             | 75            | 82  |
| **Consulta**       | 83         | 87             | 78            | 82  | 56         | 87             | 75            | 82  |
| **Calendário**     | 67         | 91             | 74            | 82  | 48         | 91             | 71            | 82  |
| **Todas as Reservas**| 82      | 90             | 74            | 82  | 56         | 85             | 75            | 82  |
| **Listar Lotes**   | 78         | 90             | 74            | 91  | 57         | 85             | 75            | 91  |
| **Nova Sala**      | 86         | 93             | 74            | 91  | 58         | 92             | 75            | 91  |
| **Listar Salas**   | 84         | 90             | 74            | 91  | 58         | 85             | 75            | 91  |
| **Usuários**       | 84         | 90             | 74            | 91  | 57         | 85             | 75            | 91  |
| **Meu Perfil**     | 87         | 93             | 74            | 91  | 58         | 92             | 75            | 91  |
| **Ativar Conta**   | 88         | 95             | 78            | 91  | 57         | 95             | 79            | 90  |
| **Login**          | 86         | 96             | 74            | 91  | 58         | 96             | 71            | 91  |
| **Cadastro**       | 87         | 96             | 74            | 91  | 58         | 96             | 74            | 91  |
| **Recuperar Senha**| 87         | 95             | 74            | 91  | 58         | 95             | 71            | 91  |
| **MÉDIA**          | 83,5       | 93             | 74            | 91  | 57         | 89,5           | 75            | 91  |


## 🔍 Análise - Lighthouse

A análise foi realizada diante dos relatórios gerados. Para acessar todos os erros identificados dado pelo diagnóstico, clique no seguinte link: [Teste de Desempenho](https://github.com/liviabeatrizml/Trabalho-de-Conclusao-de-Curso/blob/main/docs/Teste_de_Desempenho.pdf).

### Desktop

| Gravidade | Desempenho | Acessibilidade | Boas Práticas | SEO |
|-----------|------------|----------------|---------------|-----|
| Alta      | 135        | 33             | 48            | 23  |
| Média     | 41         | 0              | 0             | 0   |
| Baixa     | 124        | 13             | 32            | 0   |
| TOTAL     | 300        | 46             | 80            | 23  |


### Mobile

| Gravidade | Desempenho | Acessibilidade | Boas Práticas | SEO |
|-----------|------------|----------------|---------------|-----|
| Alta      | 135        | 40             | 50            | 22  |
| Média     | 48         | 0              | 0             | 0   |
| Baixa     | 132        | 14             | 32            | 0   |
| TOTAL     | 315        | 54             | 82            | 22  |


## 📊 Resultados dos Testes - JMeter

| Páginas          | Amostras | Média | Mediana | 90% das amostras processadas em: | 95% das amostras processadas em: | 99% das amostras processadas em: | Mín. | Max. | Desvio Padrão | % de Erro | Vazão   | KB/s  | Send KB/s | Média de Bytes |
|------------------|----------|-------|---------|----------------------------------|----------------------------------|----------------------------------|------|------|---------------|-----------|---------|-------|-----------|----------------|
| **Login**        | 360      | 2023  | 2093    | 2625                             | 2818                             | 3030                             | 558  | 3414 | 533,83        | 0,00%     | 104,96  | 558,89| 15,68     | 5452,8         |
|                  | 720      | 9465  | 9294    | 10098                            | 10241                            | 10520                            | 8871 | 10573| 433,42        | 44,72%    | 67,97   | 262,61| 5,61      | 3956,4         |
|                  | 1440     | 9990  | 9684    | 15835                            | 16493                            | 16942                            | 5927 | 16998| 2513,30       | 56,25%    | 84,45   | 329,97| 5,52      | 4001,2         |
| **Cadastro**     | 360      | 379   | 382     | 563                              | 608                              | 719                              | 104  | 762  | 136,95        | 0,00%     | 228,86  | 1736,29| 35,54     | 7768,7         |
|                  | 720      | 2093  | 2169    | 2977                             | 3148                             | 3471                             | 575  | 3531 | 677,91        | 17,64%    | 184,24  | 1242,73| 23,56     | 6907,2         |
|                  | 1440     | 1365  | 983     | 3447                             | 3562                             | 3712                             | 104  | 5471 | 1128,75       | 0,00%     | 177,25  | 1344,75| 27,52     | 7768,7         |
| **Recuperar Senha** | 360   | 278   | 264     | 334                              | 400                              | 1212                             | 106  | 1230 | 160,97        | 0,00%     | 160,07  | 918,80 | 25,64     | 5877,7         |
|                  | 720      | 964   | 599     | 1938                             | 2262                             | 3238                             | 103  | 3926 | 738,17        | 0,00%     | 147,48  | 846,53 | 23,62     | 5877,7         |
|                  | 1440     | 1378  | 1402    | 2346                             | 3005                             | 3333                             | 113  | 5846 | 785,24        | 0,00%     | 160,93  | 923,74 | 25,77     | 5877,7         |

## 🔍 Análise - JMeter

#### Login

| Página | Amostra | Média | Mín. | Máx. | Vazão | % de Erro |
|--------|---------|-------|------|------|-------|-----------|
| Login  | 360     | 558   | 558  | 3414 | 104,96| 0,00%     |
|        | 720     | 8871  | 575  | 10573| 67,97 | 44,72%    |
|        | 1440    | 9990  | 5927 | 16998| 84,45 | 56,25%    |

#### Cadastro

| Página       | Amostra | Média | Mín. | Máx. | Vazão | % de Erro |
|--------------|---------|-------|------|------|-------|-----------|
| **Cadastro** | 360     | 104   | 104  | 762  | 228,86| 0,00%     |
|              | 720     | 2093  | 575  | 3531 | 184,23| 17,64%    |
|              | 1440    | 1365  | 104  | 5471 | 177,25| 0,00%     |

#### Recuperar Senha

| Página             | Amostra | Média | Mín. | Máx. | Vazão | % de Erro |
|--------------------|---------|-------|------|------|-------|-----------|
| **Recuperar Senha**| 360     | 278   | 106  | 1230 | 160,07| 0,00%     |
|                    | 720     | 964   | 103  | 3926 | 147,48| 0,00%     |
|                    | 1440    | 1378  | 113  | 5846 | 160,92| 0,00%     |

---
[🔙 Voltar](../tests/introducao.md/#️-roteiro-de-teste)