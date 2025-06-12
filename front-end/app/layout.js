import './globals.css';

/**
 * Metadados da aplicação usados para configurar título e descrição da página.
 * Podem ser utilizados por bibliotecas de SEO como o `next/head`.
 *
 * @constant
 * @type {{ title: string, description: string }}
 */
export const metadata = {
  title: "PsiConnect",
  description: "Psicologia Online",
};

/**
 * Componente `RootLayout` define a estrutura base (HTML e BODY) de toda a aplicação Next.js.
 * Aplica fontes e favicon globais, além de envolver todas as páginas com HTML padrão.
 *
 * @component
 * @param {Object} props - Propriedades do componente.
 * @param {React.ReactNode} props.children - Elementos filhos renderizados dentro do body.
 * @returns {JSX.Element} Estrutura HTML base da aplicação.
 */
export default function RootLayout({ children }) {
  return (
    <html lang="pt-br">
      <head>
        {/* Ícone da aba do navegador */}
        <link rel="icon" href="/psiconnect-logo.png" type="image/png" />

        {/* Fonte do Google para ícones (Material Symbols) */}
        <link
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined"
          rel="stylesheet"
        />
      </head>
      <body>{children}</body>
    </html>
  );
}
