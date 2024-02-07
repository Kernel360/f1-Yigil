# Node.js 18을 기반으로한 공식 이미지를 베이스로 사용합니다.
FROM node:18-alpine AS base

# 앱 의존성 설치를 위한 단계를 정의합니다.
FROM base AS deps
WORKDIR /app

# 패키지 관리자에 따라 적절한 종속성을 설치합니다.
COPY package.json yarn.lock* package-lock.json* ./
RUN npm install --frozen-lockfile

# 소스 코드 빌드를 위한 단계를 정의합니다.
FROM base AS builder
WORKDIR /app
COPY . .
COPY --from=deps /app/node_modules ./node_modules

# 프로젝트를 빌드합니다.
RUN npm run build

# 프로덕션 이미지를 위한 단계를 정의합니다.
FROM nginx:latest AS production
WORKDIR /usr/share/nginx/html

EXPOSE @YIGIL_ADMIN_FRONT_PORT@

# 빌드된 프로젝트 파일을 Nginx에 제공할 수 있는 위치로 복사합니다.
COPY --from=builder /app/dist .

# Nginx를 실행합니다.
CMD ["nginx", "-g", "daemon off;"]
