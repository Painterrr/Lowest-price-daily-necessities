# Lowest-price-daily-necessities

## 프로젝트 이름
생필품 최저가 검색 서비스

## 프로젝트 목적
DB Sharding과 Partitioning을 활용하여 대용량 데이터 조회 시 검색 속도 개선

## 기간
2024.06

## 설명
- 데이터: 한국소비자원_생필품 가격 정보_20240517, 국토교통부_전국 법정동_20240513(출처: 공공데이터 포털)
  - 한국소비자원_생필품 가격 정보_20240517 데이터 21만 건

- area 데이터 3885행 insert 14초
- mart 데이터 211799행 insert 30분
- product 데이터 211799행 insert 30분

- 고객 객체
  - std: DB 성능 개선 로직 적용 전 테스트
  - shd: sharding 적용 후 테스트
  - ptn: partitioning 적용 후 테스트

1. 고객객체 별 진행(std, shd, ptn)
2. [지역선택] ex) 서울특별시 송파구 가락1동
3. [상품 선택] ex) 허니버터 아몬드
4. 상품 평균 최저가 판매처 제공(근처 기준 : 시군구 말고 동사무소 행정동 사용. 구 단위 조회)

## 프로젝트 수행 결과
- 입력값:
- std: 
- shd: 
- ptn: 
