import './globals.css';

export const metadata = {
  title: "PsiConnect",
  description: "Psicologia Online",
};

export default function RootLayout({ children }) {
  return (
    <html lang="pt-br">
      <head>
        <link rel="icon" href="/psiconnect-logo.png" type="image/png" />
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet"/>
      </head>
      <body>{children}</body>
    </html>
  );
}
