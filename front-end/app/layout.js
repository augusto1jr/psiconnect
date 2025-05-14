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
      </head>
      <body>{children}</body>
    </html>
  );
}
