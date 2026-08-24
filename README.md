## About

A Spring Boot expense tracker demonstrating modern backend practices, including stateless JWT authentication, refresh token rotation, session management, and PostgreSQL persistence.


flowchart TD

```mermaid
    %% =========================
    %% LOGIN
    %% =========================

    A[Client / Bruno / Browser] -->|POST /auth/login<br/>username + password| B[AuthController]

    B --> C[AuthenticationManager]

    C --> D[DatabaseUserDetailsService]

    D --> E[(PostgreSQL<br/>users)]

    E -->|User| D

    D --> F[CustomUserDetails]

    F --> C

    C -->|Authentication successful| B

    B --> G[JWTService]

    G -->|Generate JWT<br/>15 minute expiration| H[Access Token]

    B --> I[UserSessionService]

    I --> J[Generate random<br/>Refresh Token]

    J --> K[SHA-256 hash]

    K --> L[(PostgreSQL<br/>user_sessions)]

    I --> L

    B --> M[LoginResponse]

    H --> M
    J --> M

    M -->|accessToken + refreshToken| A


    %% =========================
    %% API REQUEST
    %% =========================

    A -->|Authorization: Bearer JWT| N[Protected API]

    N --> O[SecurityFilterChain]

    O --> P[JWTAuthenticationFilter]

    P --> Q{Bearer token<br/>present?}

    Q -->|No| R[Continue filter chain]

    Q -->|Yes| S[JWTService<br/>Validate signature + expiration]

    S --> T{JWT valid?}

    T -->|No| U[No Authentication]

    T -->|Yes| V[Extract JWTPrincipal]

    V --> W[Create CustomUserDetails<br/>from JWT claims]

    W --> X[SecurityContext]

    X --> Y[Continue filter chain]

    U --> Z[401 Unauthorized]

    Y --> AA[Controller / API]

    R --> AA

    AA --> AB{Authenticated?}

    AB -->|No| Z

    AB -->|Yes| AC[Business Logic]

    AC --> AD[200 OK]


    %% =========================
    %% ACCESS TOKEN EXPIRATION
    %% =========================

    S --> AE{Access token<br/>expired?}

    AE -->|Yes| U

    AE -->|No| V


    %% =========================
    %% REFRESH
    %% =========================

    A -->|POST /auth/refresh<br/>refreshToken| AF[AuthController]

    AF --> AG[UserSessionService]

    AG --> AH[Hash incoming<br/>refresh token]

    AH --> AI[(PostgreSQL<br/>user_sessions)]

    AI --> AJ{Session found?}

    AJ -->|No| AK[401 Unauthorized]

    AJ -->|Yes| AL{Revoked?}

    AL -->|Yes| AK

    AL -->|No| AM{Expired?}

    AM -->|Yes| AK

    AM -->|No| AN[Valid refresh token]

    AN --> AO[Load associated User]

    AO --> AP[Generate new Access Token]

    AP --> AQ[Return Access Token]


    %% =========================
    %% ROTATION
    %% =========================

    AN --> AR[Generate new Refresh Token]

    AR --> AS[Hash new Refresh Token]

    AS --> AT[Replace old<br/>refresh_token_hash]

    AT --> AU[Update last_used_at]

    AU --> AV[Save SAME UserSession]

    AV --> AW[Return new Refresh Token]

    AQ --> AX[LoginResponse]

    AW --> AX

    AX --> A


    %% =========================
    %% OLD TOKEN REUSE
    %% =========================

    A -->|Old Refresh Token A| AY[/auth/refresh]

    AY --> AZ[Hash Token A]

    AZ --> BA[(user_sessions)]

    BA --> BB{Hash exists?}

    BB -->|No| BC[401 Unauthorized]

    BB -->|Yes| BD[Continue validation]

    AT -.->|Old hash A removed| BB


    %% =========================
    %% REVOCATION
    %% =========================

    BE[Logout / Revoke Session] --> BF[UserSessionService]

    BF --> BG[(PostgreSQL<br/>user_sessions)]

    BG --> BH[Set revoked_at]

    BH --> BI[Session revoked]

    BI -->|Future refresh| BJ[401 Unauthorized]
```

